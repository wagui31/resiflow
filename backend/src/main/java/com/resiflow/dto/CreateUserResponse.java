package com.resiflow.dto;

import com.resiflow.entity.User;
import com.resiflow.entity.UserRole;

public class CreateUserResponse extends UserResponse {

    public CreateUserResponse(final Long id, final String email, final Long residenceId, final UserRole role) {
        super(id, email, residenceId, role);
    }

    public static CreateUserResponse fromUser(final User user) {
        return new CreateUserResponse(
                user.getId(),
                user.getEmail(),
                user.getResidenceId(),
                user.getRole()
        );
    }
}
