package com.resiflow.dto;

public class LoginResponse {

    private final Long userId;
    private final String email;
    private final Long residenceId;

    public LoginResponse(final Long userId, final String email, final Long residenceId) {
        this.userId = userId;
        this.email = email;
        this.residenceId = residenceId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public Long getResidenceId() {
        return residenceId;
    }
}
