package com.resiflow.service;

import com.resiflow.dto.CreateUserRequest;
import com.resiflow.entity.User;
import com.resiflow.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(final CreateUserRequest request) {
        validateRequest(request);

        User user = new User();
        user.setEmail(request.getEmail().trim());
        user.setPassword(request.getPassword().trim());
        user.setResidenceId(request.getResidenceId());

        return userRepository.save(user);
    }

    private void validateRequest(final CreateUserRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Create user request must not be null");
        }
        if (isBlank(request.getEmail())) {
            throw new IllegalArgumentException("Email must not be blank");
        }
        if (isBlank(request.getPassword())) {
            throw new IllegalArgumentException("Password must not be blank");
        }
        if (request.getResidenceId() == null) {
            throw new IllegalArgumentException("Residence ID must not be null");
        }
    }

    private boolean isBlank(final String value) {
        return value == null || value.trim().isEmpty();
    }
}
