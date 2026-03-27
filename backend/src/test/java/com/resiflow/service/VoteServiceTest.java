package com.resiflow.service;

import com.resiflow.dto.VoteActionRequest;
import com.resiflow.dto.VoteResultResponse;
import com.resiflow.entity.Residence;
import com.resiflow.entity.User;
import com.resiflow.entity.UserRole;
import com.resiflow.entity.Vote;
import com.resiflow.entity.VoteChoix;
import com.resiflow.entity.VoteStatut;
import com.resiflow.entity.VoteUtilisateur;
import com.resiflow.repository.VoteRepository;
import com.resiflow.repository.VoteUtilisateurRepository;
import com.resiflow.security.AuthenticatedUser;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoteServiceTest {

    @Test
    void voterAcceptsNeutralChoice() {
        Vote vote = buildVote(10L, 7L, LocalDateTime.now().plusDays(1), VoteStatut.OUVERT);
        User actor = buildUser(21L, 7L);
        AtomicReference<VoteUtilisateur> savedVoteRef = new AtomicReference<>();
        VoteService voteService = new VoteService(
                voteRepositoryProxy(vote, new AtomicReference<>()),
                voteUtilisateurRepositoryProxy(savedVoteRef, Map.of(VoteChoix.POUR, 0L, VoteChoix.CONTRE, 0L, VoteChoix.NEUTRE, 0L), false),
                residenceAccessServiceStub(actor),
                depenseServiceStub()
        );

        VoteActionRequest request = new VoteActionRequest();
        request.setChoix(" neutre ");

        voteService.voter(10L, request, new AuthenticatedUser(21L, "user@example.com", 7L, UserRole.USER));

        assertThat(savedVoteRef.get()).isNotNull();
        assertThat(savedVoteRef.get().getChoix()).isEqualTo(VoteChoix.NEUTRE);
    }

    @Test
    void voterRejectsBlankChoice() {
        Vote vote = buildVote(10L, 7L, LocalDateTime.now().plusDays(1), VoteStatut.OUVERT);
        VoteService voteService = new VoteService(
                voteRepositoryProxy(vote, new AtomicReference<>()),
                voteUtilisateurRepositoryProxy(new AtomicReference<>(), Map.of(VoteChoix.POUR, 0L, VoteChoix.CONTRE, 0L, VoteChoix.NEUTRE, 0L), false),
                residenceAccessServiceStub(buildUser(21L, 7L)),
                depenseServiceStub()
        );

        VoteActionRequest request = new VoteActionRequest();
        request.setChoix("   ");

        assertThatThrownBy(() -> voteService.voter(10L, request, new AuthenticatedUser(21L, "user@example.com", 7L, UserRole.USER)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Vote choice must not be blank");
    }

    @Test
    void getVoteResultReturnsNeutralCountAndNeutralDoesNotAffectDecision() {
        Vote vote = buildVote(10L, 7L, LocalDateTime.now().minusMinutes(1), VoteStatut.OUVERT);
        AtomicReference<Vote> savedVoteRef = new AtomicReference<>();
        VoteService voteService = new VoteService(
                voteRepositoryProxy(vote, savedVoteRef),
                voteUtilisateurRepositoryProxy(
                        new AtomicReference<>(),
                        Map.of(VoteChoix.POUR, 2L, VoteChoix.CONTRE, 1L, VoteChoix.NEUTRE, 5L),
                        false
                ),
                residenceAccessServiceStub(buildUser(21L, 7L)),
                depenseServiceStub()
        );

        VoteResultResponse result = voteService.getVoteResult(10L, new AuthenticatedUser(21L, "user@example.com", 7L, UserRole.USER));

        assertThat(result.getTotalPour()).isEqualTo(2L);
        assertThat(result.getTotalContre()).isEqualTo(1L);
        assertThat(result.getTotalNeutre()).isEqualTo(5L);
        assertThat(result.getStatut()).isEqualTo(VoteStatut.VALIDE);
        assertThat(savedVoteRef.get()).isNotNull();
        assertThat(savedVoteRef.get().getStatut()).isEqualTo(VoteStatut.VALIDE);
    }

    private VoteRepository voteRepositoryProxy(final Vote voteToReturn, final AtomicReference<Vote> savedVoteRef) {
        return (VoteRepository) Proxy.newProxyInstance(
                VoteRepository.class.getClassLoader(),
                new Class<?>[]{VoteRepository.class},
                (proxy, method, args) -> {
                    if ("findById".equals(method.getName())) {
                        return Optional.of(voteToReturn);
                    }
                    if ("save".equals(method.getName())) {
                        Vote vote = (Vote) args[0];
                        savedVoteRef.set(vote);
                        return vote;
                    }
                    if ("findAllByStatutAndDateFinBefore".equals(method.getName())) {
                        return List.of();
                    }
                    if ("toString".equals(method.getName())) {
                        return "VoteRepositoryTestProxy";
                    }
                    if ("hashCode".equals(method.getName())) {
                        return System.identityHashCode(proxy);
                    }
                    if ("equals".equals(method.getName())) {
                        return proxy == args[0];
                    }
                    throw new UnsupportedOperationException("Unsupported method: " + method.getName());
                });
    }

    private VoteUtilisateurRepository voteUtilisateurRepositoryProxy(
            final AtomicReference<VoteUtilisateur> savedVoteRef,
            final Map<VoteChoix, Long> counts,
            final boolean alreadyVoted
    ) {
        return (VoteUtilisateurRepository) Proxy.newProxyInstance(
                VoteUtilisateurRepository.class.getClassLoader(),
                new Class<?>[]{VoteUtilisateurRepository.class},
                (proxy, method, args) -> {
                    if ("existsByVote_IdAndUtilisateur_Id".equals(method.getName())) {
                        return alreadyVoted;
                    }
                    if ("countByVote_IdAndChoix".equals(method.getName())) {
                        VoteChoix choix = (VoteChoix) args[1];
                        return counts.getOrDefault(choix, 0L);
                    }
                    if ("save".equals(method.getName())) {
                        VoteUtilisateur voteUtilisateur = (VoteUtilisateur) args[0];
                        savedVoteRef.set(voteUtilisateur);
                        return voteUtilisateur;
                    }
                    if ("toString".equals(method.getName())) {
                        return "VoteUtilisateurRepositoryTestProxy";
                    }
                    if ("hashCode".equals(method.getName())) {
                        return System.identityHashCode(proxy);
                    }
                    if ("equals".equals(method.getName())) {
                        return proxy == args[0];
                    }
                    throw new UnsupportedOperationException("Unsupported method: " + method.getName());
                });
    }

    private ResidenceAccessService residenceAccessServiceStub(final User actor) {
        return new ResidenceAccessService(null, null) {
            @Override
            public User getRequiredActor(final AuthenticatedUser authenticatedUser) {
                return actor;
            }

            @Override
            public void ensureMemberAccessToResidence(final AuthenticatedUser authenticatedUser, final Long residenceId) {
                // No-op for service unit tests.
            }

            @Override
            public void ensureAdminAccessToResidence(final AuthenticatedUser authenticatedUser, final Long residenceId) {
                // No-op for service unit tests.
            }
        };
    }

    private DepenseService depenseServiceStub() {
        return new DepenseService(null, null, null) {
        };
    }

    private Vote buildVote(final Long voteId, final Long residenceId, final LocalDateTime dateFin, final VoteStatut statut) {
        Residence residence = new Residence();
        residence.setId(residenceId);

        Vote vote = new Vote();
        vote.setId(voteId);
        vote.setResidence(residence);
        vote.setTitre("Vote travaux");
        vote.setDescription("Description");
        vote.setStatut(statut);
        vote.setDateDebut(LocalDateTime.now().minusDays(1));
        vote.setDateFin(dateFin);
        vote.setCreePar(buildUser(99L, residenceId));
        return vote;
    }

    private User buildUser(final Long userId, final Long residenceId) {
        User user = new User();
        user.setId(userId);
        user.setResidenceId(residenceId);
        user.setRole(UserRole.USER);
        return user;
    }
}
