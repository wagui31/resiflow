package com.resiflow.service;

import com.resiflow.dto.CreateInvitationRequest;
import com.resiflow.dto.InvitationResponse;
import com.resiflow.entity.Invitation;
import com.resiflow.entity.UserRole;
import com.resiflow.repository.InvitationRepository;
import com.resiflow.security.AuthenticatedUser;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InvitationServiceTest {

    @Test
    void createInvitationSavesInvitationForAuthenticatedUserResidence() {
        AtomicReference<Invitation> savedInvitationRef = new AtomicReference<>();
        InvitationService invitationService = new InvitationService(repositoryProxy(savedInvitationRef));

        CreateInvitationRequest request = new CreateInvitationRequest();
        request.setTargetValue(" resident@example.com ");
        request.setExpiresAt(LocalDateTime.now().plusDays(3));

        AuthenticatedUser authenticatedUser = new AuthenticatedUser(9L, "admin@example.com", 7L, UserRole.ADMIN);

        InvitationResponse response = invitationService.createInvitation(request, authenticatedUser);

        Invitation invitationToSave = savedInvitationRef.get();
        assertThat(invitationToSave.getResidenceId()).isEqualTo(7L);
        assertThat(invitationToSave.getTargetValue()).isEqualTo("resident@example.com");
        assertThat(invitationToSave.getCreatedBy()).isEqualTo(9L);
        assertThat(invitationToSave.getStatus()).isEqualTo("ACTIVE");
        assertThat(invitationToSave.getToken()).isNotBlank();
        assertThat(invitationToSave.getCreatedAt()).isNotNull();

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getResidenceId()).isEqualTo(7L);
        assertThat(response.getTargetValue()).isEqualTo("resident@example.com");
        assertThat(response.getCreatedBy()).isEqualTo(9L);
        assertThat(response.getStatus()).isEqualTo("ACTIVE");
    }

    @Test
    void createInvitationFromUserRequiresAdminValidation() {
        AtomicReference<Invitation> savedInvitationRef = new AtomicReference<>();
        InvitationService invitationService = new InvitationService(repositoryProxy(savedInvitationRef));

        CreateInvitationRequest request = new CreateInvitationRequest();
        request.setTargetValue(" resident@example.com ");
        request.setExpiresAt(LocalDateTime.now().plusDays(3));

        InvitationResponse response = invitationService.createInvitation(
                request,
                new AuthenticatedUser(9L, "user@example.com", 7L, UserRole.USER)
        );

        assertThat(savedInvitationRef.get().getStatus()).isEqualTo("PENDING_ADMIN_VALIDATION");
        assertThat(response.getStatus()).isEqualTo("PENDING_ADMIN_VALIDATION");
    }

    @Test
    void createInvitationRejectsBlankTargetValue() {
        InvitationService invitationService = new InvitationService(repositoryProxy(new AtomicReference<>()));

        CreateInvitationRequest request = new CreateInvitationRequest();
        request.setTargetValue(" ");
        request.setExpiresAt(LocalDateTime.now().plusDays(1));

        assertThatThrownBy(() -> invitationService.createInvitation(
                request,
                new AuthenticatedUser(1L, "admin@example.com", 7L, UserRole.ADMIN)
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Target value must not be blank");
    }

    @Test
    void createInvitationRejectsNullRequest() {
        InvitationService invitationService = new InvitationService(repositoryProxy(new AtomicReference<>()));

        assertThatThrownBy(() -> invitationService.createInvitation(
                null,
                new AuthenticatedUser(1L, "admin@example.com", 7L, UserRole.ADMIN)
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Create invitation request must not be null");
    }

    @Test
    void createInvitationRejectsMissingExpirationDate() {
        InvitationService invitationService = new InvitationService(repositoryProxy(new AtomicReference<>()));

        CreateInvitationRequest request = new CreateInvitationRequest();
        request.setTargetValue("resident@example.com");

        assertThatThrownBy(() -> invitationService.createInvitation(
                request,
                new AuthenticatedUser(1L, "admin@example.com", 7L, UserRole.ADMIN)
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Expiration date must not be null");
    }

    @Test
    void createInvitationRejectsPastExpirationDate() {
        InvitationService invitationService = new InvitationService(repositoryProxy(new AtomicReference<>()));

        CreateInvitationRequest request = new CreateInvitationRequest();
        request.setTargetValue("resident@example.com");
        request.setExpiresAt(LocalDateTime.now().minusMinutes(1));

        assertThatThrownBy(() -> invitationService.createInvitation(
                request,
                new AuthenticatedUser(1L, "admin@example.com", 7L, UserRole.ADMIN)
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Expiration date must be in the future");
    }

    @Test
    void createInvitationRejectsMissingAuthenticatedUserResidenceId() {
        InvitationService invitationService = new InvitationService(repositoryProxy(new AtomicReference<>()));

        CreateInvitationRequest request = new CreateInvitationRequest();
        request.setTargetValue("resident@example.com");
        request.setExpiresAt(LocalDateTime.now().plusDays(1));

        assertThatThrownBy(() -> invitationService.createInvitation(
                request,
                new AuthenticatedUser(1L, "admin@example.com", null, UserRole.ADMIN)
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Authenticated user residence ID must not be null");
    }

    @Test
    void createInvitationRejectsNullAuthenticatedUser() {
        InvitationService invitationService = new InvitationService(repositoryProxy(new AtomicReference<>()));

        CreateInvitationRequest request = new CreateInvitationRequest();
        request.setTargetValue("resident@example.com");
        request.setExpiresAt(LocalDateTime.now().plusDays(1));

        assertThatThrownBy(() -> invitationService.createInvitation(request, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Authenticated user must not be null");
    }

    @Test
    void createInvitationRejectsMissingAuthenticatedUserId() {
        InvitationService invitationService = new InvitationService(repositoryProxy(new AtomicReference<>()));

        CreateInvitationRequest request = new CreateInvitationRequest();
        request.setTargetValue("resident@example.com");
        request.setExpiresAt(LocalDateTime.now().plusDays(1));

        assertThatThrownBy(() -> invitationService.createInvitation(
                request,
                new AuthenticatedUser(null, "admin@example.com", 7L, UserRole.ADMIN)
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Authenticated user ID must not be null");
    }

    private InvitationRepository repositoryProxy(final AtomicReference<Invitation> savedInvitationRef) {
        return (InvitationRepository) Proxy.newProxyInstance(
                InvitationRepository.class.getClassLoader(),
                new Class<?>[]{InvitationRepository.class},
                (proxy, method, args) -> {
                    if ("save".equals(method.getName())) {
                        Invitation invitation = (Invitation) args[0];
                        savedInvitationRef.set(invitation);

                        Invitation savedInvitation = new Invitation();
                        savedInvitation.setId(1L);
                        savedInvitation.setResidenceId(invitation.getResidenceId());
                        savedInvitation.setTargetValue(invitation.getTargetValue());
                        savedInvitation.setToken(invitation.getToken());
                        savedInvitation.setStatus(invitation.getStatus());
                        savedInvitation.setExpiresAt(invitation.getExpiresAt());
                        savedInvitation.setCreatedBy(invitation.getCreatedBy());
                        savedInvitation.setCreatedAt(invitation.getCreatedAt());
                        return savedInvitation;
                    }
                    if ("toString".equals(method.getName())) {
                        return "InvitationRepositoryTestProxy";
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
}
