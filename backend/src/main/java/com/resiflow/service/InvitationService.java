package com.resiflow.service;

import com.resiflow.dto.CreateInvitationRequest;
import com.resiflow.dto.InvitationResponse;
import com.resiflow.entity.Invitation;
import com.resiflow.repository.InvitationRepository;
import com.resiflow.security.AuthenticatedUser;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class InvitationService {

    private static final String INITIAL_STATUS = "ACTIVE";

    private final InvitationRepository invitationRepository;

    public InvitationService(final InvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }

    public InvitationResponse createInvitation(
            final CreateInvitationRequest request,
            final AuthenticatedUser authenticatedUser
    ) {
        validateRequest(request);
        validateAuthenticatedUser(authenticatedUser);

        LocalDateTime now = LocalDateTime.now();

        Invitation invitation = new Invitation();
        invitation.setResidenceId(authenticatedUser.residenceId());
        invitation.setTargetValue(request.getTargetValue().trim());
        invitation.setToken(UUID.randomUUID().toString());
        invitation.setStatus(INITIAL_STATUS);
        invitation.setExpiresAt(request.getExpiresAt());
        invitation.setCreatedBy(authenticatedUser.userId());
        invitation.setCreatedAt(now);

        Invitation savedInvitation = invitationRepository.save(invitation);

        return new InvitationResponse(
                savedInvitation.getId(),
                savedInvitation.getResidenceId(),
                savedInvitation.getTargetValue(),
                savedInvitation.getToken(),
                savedInvitation.getStatus(),
                savedInvitation.getExpiresAt(),
                savedInvitation.getCreatedBy(),
                savedInvitation.getCreatedAt()
        );
    }

    private void validateRequest(final CreateInvitationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Create invitation request must not be null");
        }
        if (isBlank(request.getTargetValue())) {
            throw new IllegalArgumentException("Target value must not be blank");
        }
        if (request.getExpiresAt() == null) {
            throw new IllegalArgumentException("Expiration date must not be null");
        }
        if (!request.getExpiresAt().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Expiration date must be in the future");
        }
    }

    private void validateAuthenticatedUser(final AuthenticatedUser authenticatedUser) {
        if (authenticatedUser == null) {
            throw new IllegalArgumentException("Authenticated user must not be null");
        }
        if (authenticatedUser.userId() == null) {
            throw new IllegalArgumentException("Authenticated user ID must not be null");
        }
        if (authenticatedUser.residenceId() == null) {
            throw new IllegalArgumentException("Authenticated user residence ID must not be null");
        }
    }

    private boolean isBlank(final String value) {
        return value == null || value.trim().isEmpty();
    }
}
