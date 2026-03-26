package com.resiflow.service;

import com.resiflow.dto.CreateAdminRequest;
import com.resiflow.dto.CreateUserRequest;
import com.resiflow.entity.Residence;
import com.resiflow.entity.User;
import com.resiflow.entity.UserRole;
import com.resiflow.repository.UserRepository;
import com.resiflow.security.AuthenticatedUser;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ResidenceService residenceService;

    public UserService(
            final UserRepository userRepository,
            final PasswordEncoder passwordEncoder,
            final ResidenceService residenceService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.residenceService = residenceService;
    }

    public User createAdmin(final CreateAdminRequest request) {
        validateAdminRequest(request);
        ensureEmailAvailable(request.getEmail());

        Residence residence = residenceService.getRequiredResidence(request.getResidenceId());

        User user = new User();
        user.setEmail(request.getEmail().trim());
        user.setPassword(passwordEncoder.encode(request.getPassword().trim()));
        user.setResidence(residence);
        user.setRole(UserRole.ADMIN);

        return userRepository.save(user);
    }

    public User createResidenceUser(final CreateUserRequest request, final AuthenticatedUser authenticatedUser) {
        validateRequest(request);
        AuthenticatedUser actor = requireAuthenticatedUser(authenticatedUser);
        requireRole(actor, UserRole.ADMIN);
        ensureEmailAvailable(request.getEmail());

        Residence residence = residenceService.getRequiredResidence(actor.residenceId());

        if (request.getResidenceId() != null && !request.getResidenceId().equals(actor.residenceId())) {
            throw new AccessDeniedException("Cross-residence access is forbidden");
        }

        User user = new User();
        user.setEmail(request.getEmail().trim());
        user.setPassword(passwordEncoder.encode(request.getPassword().trim()));
        user.setResidence(residence);
        user.setRole(UserRole.USER);

        return userRepository.save(user);
    }

    public List<User> getUsers(final AuthenticatedUser authenticatedUser) {
        AuthenticatedUser actor = requireAuthenticatedUser(authenticatedUser);

        if (actor.role() == UserRole.SUPER_ADMIN) {
            return userRepository.findAll();
        }
        if (actor.role() == UserRole.ADMIN) {
            requireResidenceId(actor.residenceId());
            return userRepository.findAllByResidence_Id(actor.residenceId());
        }

        throw new AccessDeniedException("Insufficient role to access users");
    }

    public User getRequiredUserInResidence(final Long userId, final Long residenceId) {
        requireResidenceId(residenceId);
        return userRepository.findByIdAndResidence_Id(userId, residenceId)
                .orElseThrow(() -> new NoSuchElementException("User not found in residence: " + userId));
    }

    private void validateAdminRequest(final CreateAdminRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Create admin request must not be null");
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
    }

    private void ensureEmailAvailable(final String email) {
        String normalizedEmail = email.trim();
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new IllegalArgumentException("Email is already used");
        }
    }

    private AuthenticatedUser requireAuthenticatedUser(final AuthenticatedUser authenticatedUser) {
        if (authenticatedUser == null) {
            throw new IllegalArgumentException("Authenticated user must not be null");
        }
        if (authenticatedUser.userId() == null) {
            throw new IllegalArgumentException("Authenticated user ID must not be null");
        }
        return authenticatedUser;
    }

    private void requireRole(final AuthenticatedUser authenticatedUser, final UserRole role) {
        if (authenticatedUser.role() != role) {
            throw new AccessDeniedException("Insufficient role for this operation");
        }
    }

    private void requireResidenceId(final Long residenceId) {
        if (residenceId == null) {
            throw new IllegalArgumentException("Residence ID must not be null");
        }
    }

    private boolean isBlank(final String value) {
        return value == null || value.trim().isEmpty();
    }
}
