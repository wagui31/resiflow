package com.resiflow.dto;

public class LoginResponse {

    private final Long userId;
    private final String email;
    private final Long residenceId;
    private final String token;

    public LoginResponse(final Long userId, final String email, final Long residenceId, final String token) {
        this.userId = userId;
        this.email = email;
        this.residenceId = residenceId;
        this.token = token;
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

    public String getToken() {
        return token;
    }
}
