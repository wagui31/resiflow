package com.resiflow.controller;

import com.resiflow.dto.CreateInvitationRequest;
import com.resiflow.dto.InvitationResponse;
import com.resiflow.security.AuthenticatedUser;
import com.resiflow.service.InvitationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/invitations", "/api/invitations"})
public class InvitationController {

    private final InvitationService invitationService;

    public InvitationController(final InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<InvitationResponse> createInvitation(
            @RequestBody final CreateInvitationRequest request,
            final Authentication authentication
    ) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();
        InvitationResponse response = invitationService.createInvitation(request, authenticatedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{invitationId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InvitationResponse> approveInvitation(
            @PathVariable final Long invitationId,
            final Authentication authentication
    ) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();
        InvitationResponse response = invitationService.approveInvitation(invitationId, authenticatedUser);
        return ResponseEntity.ok(response);
    }
}
