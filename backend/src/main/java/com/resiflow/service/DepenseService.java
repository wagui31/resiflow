package com.resiflow.service;

import com.resiflow.dto.CreateDepenseRequest;
import com.resiflow.entity.Depense;
import com.resiflow.entity.Residence;
import com.resiflow.entity.StatutDepense;
import com.resiflow.entity.User;
import com.resiflow.repository.DepenseRepository;
import com.resiflow.security.AuthenticatedUser;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepenseService {

    private final DepenseRepository depenseRepository;
    private final ResidenceAccessService residenceAccessService;
    private final TransactionCagnotteService transactionCagnotteService;

    public DepenseService(
            final DepenseRepository depenseRepository,
            final ResidenceAccessService residenceAccessService,
            final TransactionCagnotteService transactionCagnotteService
    ) {
        this.depenseRepository = depenseRepository;
        this.residenceAccessService = residenceAccessService;
        this.transactionCagnotteService = transactionCagnotteService;
    }

    @Transactional
    public Depense createDepense(final CreateDepenseRequest request, final AuthenticatedUser authenticatedUser) {
        validateCreateRequest(request);

        Residence residence = residenceAccessService.getResidenceForAdmin(request.getResidenceId(), authenticatedUser);
        User actor = residenceAccessService.getRequiredActor(authenticatedUser);

        Depense depense = new Depense();
        depense.setResidence(residence);
        depense.setMontant(request.getMontant());
        depense.setDescription(request.getDescription().trim());
        depense.setStatut(StatutDepense.EN_ATTENTE);
        depense.setCreePar(actor);
        depense.setDateCreation(LocalDateTime.now());

        return depenseRepository.save(depense);
    }

    @Transactional
    public Depense approuverDepense(final Long depenseId, final AuthenticatedUser authenticatedUser) {
        Depense depense = getRequiredDepense(depenseId);
        residenceAccessService.ensureAdminAccessToResidence(authenticatedUser, depense.getResidence().getId());
        if (depense.getStatut() != StatutDepense.EN_ATTENTE) {
            throw new IllegalStateException("Depense is already processed");
        }

        depense.setStatut(StatutDepense.APPROUVEE);
        depense.setValidePar(residenceAccessService.getRequiredActor(authenticatedUser));
        depense.setDateValidation(LocalDateTime.now());
        Depense savedDepense = depenseRepository.save(depense);
        transactionCagnotteService.createDepenseTransaction(savedDepense);
        return savedDepense;
    }

    @Transactional
    public Depense rejeterDepense(final Long depenseId, final AuthenticatedUser authenticatedUser) {
        Depense depense = getRequiredDepense(depenseId);
        residenceAccessService.ensureAdminAccessToResidence(authenticatedUser, depense.getResidence().getId());
        if (depense.getStatut() != StatutDepense.EN_ATTENTE) {
            throw new IllegalStateException("Depense is already processed");
        }

        depense.setStatut(StatutDepense.REJETEE);
        depense.setValidePar(residenceAccessService.getRequiredActor(authenticatedUser));
        depense.setDateValidation(LocalDateTime.now());
        return depenseRepository.save(depense);
    }

    @Transactional(readOnly = true)
    public List<Depense> getDepensesByResidence(final Long residenceId, final AuthenticatedUser authenticatedUser) {
        residenceAccessService.getResidenceForMember(residenceId, authenticatedUser);
        return depenseRepository.findAllByResidence_IdOrderByDateCreationDesc(residenceId);
    }

    private Depense getRequiredDepense(final Long depenseId) {
        if (depenseId == null) {
            throw new IllegalArgumentException("Depense ID must not be null");
        }
        return depenseRepository.findById(depenseId)
                .orElseThrow(() -> new NoSuchElementException("Depense not found: " + depenseId));
    }

    private void validateCreateRequest(final CreateDepenseRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Create depense request must not be null");
        }
        if (request.getResidenceId() == null) {
            throw new IllegalArgumentException("Residence ID must not be null");
        }
        if (request.getMontant() == null || request.getMontant().signum() <= 0) {
            throw new IllegalArgumentException("Depense montant must be greater than zero");
        }
        if (request.getDescription() == null || request.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Depense description must not be blank");
        }
    }
}
