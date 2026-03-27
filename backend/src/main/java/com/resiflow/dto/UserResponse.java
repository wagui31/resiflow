package com.resiflow.dto;

import com.resiflow.entity.User;
import com.resiflow.entity.UserRole;
import com.resiflow.entity.UserStatus;

public class UserResponse {

    private final Long id;
    private final String email;
    private final Long residenceId;
    private final String residenceCode;
    private final UserRole role;
    private final UserStatus status;

    public UserResponse(
            final Long id,
            final String email,
            final Long residenceId,
            final String residenceCode,
            final UserRole role,
            final UserStatus status
    ) {
        this.id = id;
        this.email = email;
        this.residenceId = residenceId;
        this.residenceCode = residenceCode;
        this.role = role;
        this.status = status;
    }

    public static UserResponse fromUser(final User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getResidenceId(),
                user.getResidence() == null ? null : user.getResidence().getCode(),
                user.getRole(),
                user.getStatus()
        );
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

    public String getResidenceCode() {
        return residenceCode;
    }

    public UserRole getRole() {
        return role;
    }

    public UserStatus getStatus() {
        return status;
    }
}
