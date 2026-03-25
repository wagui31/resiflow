package com.resiflow.service;

import com.resiflow.dto.LoginRequest;
import com.resiflow.dto.LoginResponse;
import com.resiflow.entity.User;
import com.resiflow.repository.UserRepository;
import com.resiflow.security.JwtProperties;
import com.resiflow.security.JwtService;
import java.lang.reflect.Proxy;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuthServiceTest {

    private static final String SECRET = "Zm9yLXRlc3RzLW9ubHktcmVzaWZsb3ctand0LXNlY3JldC1rZXktMzItYnl0ZXM=";
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void loginReturnsUserDataWhenCredentialsAreValid() {
        User user = new User();
        user.setId(4L);
        user.setEmail("resident@example.com");
        user.setPassword(passwordEncoder.encode("secret"));
        user.setResidenceId(12L);

        JwtService jwtService = new JwtService(new JwtProperties(SECRET, 3600000));
        AuthService authService = new AuthService(repositoryProxy(Optional.of(user)), jwtService, passwordEncoder);

        LoginRequest request = new LoginRequest();
        request.setEmail(" resident@example.com ");
        request.setPassword(" secret ");

        LoginResponse response = authService.login(request);

        assertThat(response.getUserId()).isEqualTo(4L);
        assertThat(response.getEmail()).isEqualTo("resident@example.com");
        assertThat(response.getResidenceId()).isEqualTo(12L);
        assertThat(response.getToken()).isNotBlank();
        assertThat(jwtService.extractSubject(response.getToken())).isEqualTo("resident@example.com");
    }

    @Test
    void loginRejectsBlankEmail() {
        AuthService authService = new AuthService(
                repositoryProxy(Optional.empty()),
                new JwtService(new JwtProperties(SECRET, 3600000)),
                passwordEncoder
        );

        LoginRequest request = new LoginRequest();
        request.setEmail(" ");
        request.setPassword("secret");

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email must not be blank");
    }

    @Test
    void loginRejectsUnknownEmail() {
        AuthService authService = new AuthService(
                repositoryProxy(Optional.empty()),
                new JwtService(new JwtProperties(SECRET, 3600000)),
                passwordEncoder
        );

        LoginRequest request = new LoginRequest();
        request.setEmail("resident@example.com");
        request.setPassword("secret");

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessage("Invalid credentials");
    }

    @Test
    void loginRejectsInvalidPassword() {
        User user = new User();
        user.setEmail("resident@example.com");
        user.setPassword(passwordEncoder.encode("secret"));

        AuthService authService = new AuthService(
                repositoryProxy(Optional.of(user)),
                new JwtService(new JwtProperties(SECRET, 3600000)),
                passwordEncoder
        );

        LoginRequest request = new LoginRequest();
        request.setEmail("resident@example.com");
        request.setPassword("wrong-password");

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessage("Invalid credentials");
    }

    private UserRepository repositoryProxy(final Optional<User> userToReturn) {
        return (UserRepository) Proxy.newProxyInstance(
                UserRepository.class.getClassLoader(),
                new Class<?>[]{UserRepository.class},
                (proxy, method, args) -> {
                    if ("findByEmail".equals(method.getName())) {
                        return userToReturn;
                    }
                    if ("toString".equals(method.getName())) {
                        return "UserRepositoryTestProxy";
                    }
                    if ("hashCode".equals(method.getName())) {
                        return System.identityHashCode(proxy);
                    }
                    if ("equals".equals(method.getName())) {
                        return proxy == args[0];
                    }
                    throw new UnsupportedOperationException("Unsupported method: " + method.getName());
                });
    }
}
