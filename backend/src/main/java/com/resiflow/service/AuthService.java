package com.resiflow.service;

import com.resiflow.dto.LoginRequest;
import com.resiflow.dto.LoginResponse;
import com.resiflow.entity.User;
import com.resiflow.repository.UserRepository;
import com.resiflow.security.JwtService;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {

    private static final String INVALID_CREDENTIALS_MESSAGE = "Invalid credentials";

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            final UserRepository userRepository,
            final JwtService jwtService,
            final PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(final LoginRequest request) {
        validateRequest(request);

        String email = request.getEmail().trim();
        String password = request.getPassword().trim();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException(INVALID_CREDENTIALS_MESSAGE));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException(INVALID_CREDENTIALS_MESSAGE);
        }

        String token = jwtService.generateToken(user);
        return new LoginResponse(user.getId(), user.getEmail(), user.getResidenceId(), token);
    }

    private void validateRequest(final LoginRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Login request must not be null");
        }
        if (isBlank(request.getEmail())) {
            throw new IllegalArgumentException("Email must not be blank");
        }
        if (isBlank(request.getPassword())) {
            throw new IllegalArgumentException("Password must not be blank");
        }
    }

    private boolean isBlank(final String value) {
        return value == null || value.trim().isEmpty();
    }
}
