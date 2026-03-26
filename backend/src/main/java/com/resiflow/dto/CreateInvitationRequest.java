package com.resiflow.dto;

import java.time.LocalDateTime;

public class CreateInvitationRequest {

    private String targetValue;
    private LocalDateTime expiresAt;

    public String getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(final String targetValue) {
        this.targetValue = targetValue;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(final LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
