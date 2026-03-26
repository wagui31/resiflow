package com.resiflow.dto;

import com.resiflow.entity.User;
import com.resiflow.entity.UserRole;

public class UserResponse {

    private final Long id;
    private final String email;
    private final Long residenceId;
    private final UserRole role;

    public UserResponse(final Long id, final String email, final Long residenceId, final UserRole role) {
        this.id = id;
        this.email = email;
        this.residenceId = residenceId;
        this.role = role;
    }

    public static UserResponse fromUser(final User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getResidenceId(), user.getRole());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Long getResidenceId() {
        return residenceId;
    }

    public UserRole getRole() {
        return role;
    }
}
