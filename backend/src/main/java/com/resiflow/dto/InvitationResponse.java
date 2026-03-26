package com.resiflow.dto;

import java.time.LocalDateTime;

public class InvitationResponse {

    private final Long id;
    private final Long residenceId;
    private final String targetValue;
    private final String token;
    private final String status;
    private final LocalDateTime expiresAt;
    private final Long createdBy;
    private final LocalDateTime createdAt;

    public InvitationResponse(
            final Long id,
            final Long residenceId,
            final String targetValue,
            final String token,
            final String status,
            final LocalDateTime expiresAt,
            final Long createdBy,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.residenceId = residenceId;
        this.targetValue = targetValue;
        this.token = token;
        this.status = status;
        this.expiresAt = expiresAt;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getResidenceId() {
        return residenceId;
    }

    public String getTargetValue() {
        return targetValue;
    }

    public String getToken() {
        return token;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
