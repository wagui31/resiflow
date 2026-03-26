package com.resiflow.service;

import com.resiflow.dto.CreateUserRequest;
import com.resiflow.entity.Residence;
import com.resiflow.entity.User;
import com.resiflow.entity.UserRole;
import com.resiflow.repository.UserRepository;
import com.resiflow.security.AuthenticatedUser;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceTest {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void createUserSavesUserFromRequest() {
        AtomicReference<User> savedUserRef = new AtomicReference<>();
        UserRepository userRepository = repositoryProxy(savedUserRef);
        UserService userService = new UserService(userRepository, passwordEncoder, residenceServiceStub());

        CreateUserRequest request = new CreateUserRequest();
        request.setEmail(" resident@example.com ");
        request.setPassword(" secret ");

        User result = userService.createResidenceUser(
                request,
                new AuthenticatedUser(10L, "admin@example.com", 7L, UserRole.ADMIN)
        );

        User userToSave = savedUserRef.get();
        assertThat(userToSave.getEmail()).isEqualTo("resident@example.com");
        assertThat(passwordEncoder.matches("secret", userToSave.getPassword())).isTrue();
        assertThat(userToSave.getResidenceId()).isEqualTo(7L);
        assertThat(userToSave.getRole()).isEqualTo(UserRole.USER);
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void createUserRejectsBlankEmail() {
        UserService userService = new UserService(
                repositoryProxy(new AtomicReference<>()),
                passwordEncoder,
                residenceServiceStub()
        );

        CreateUserRequest request = new CreateUserRequest();
        request.setEmail(" ");
        request.setPassword("secret");

        assertThatThrownBy(() -> userService.createResidenceUser(
                request,
                new AuthenticatedUser(10L, "admin@example.com", 7L, UserRole.ADMIN)
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email must not be blank");
    }

    @Test
    void createUserRejectsNullRequest() {
        UserService userService = new UserService(
                repositoryProxy(new AtomicReference<>()),
                passwordEncoder,
                residenceServiceStub()
        );

        assertThatThrownBy(() -> userService.createResidenceUser(
                null,
                new AuthenticatedUser(10L, "admin@example.com", 7L, UserRole.ADMIN)
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Create user request must not be null");
    }

    @Test
    void createUserRejectsBlankPassword() {
        UserService userService = new UserService(
                repositoryProxy(new AtomicReference<>()),
                passwordEncoder,
                residenceServiceStub()
        );

        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("resident@example.com");
        request.setPassword(" ");

        assertThatThrownBy(() -> userService.createResidenceUser(
                request,
                new AuthenticatedUser(10L, "admin@example.com", 7L, UserRole.ADMIN)
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Password must not be blank");
    }

    @Test
    void createUserRejectsCrossResidenceRequest() {
        UserService userService = new UserService(
                repositoryProxy(new AtomicReference<>()),
                passwordEncoder,
                residenceServiceStub()
        );

        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("resident@example.com");
        request.setPassword("secret");
        request.setResidenceId(99L);

        assertThatThrownBy(() -> userService.createResidenceUser(
                request,
                new AuthenticatedUser(10L, "admin@example.com", 7L, UserRole.ADMIN)
        ))
                .isInstanceOf(AccessDeniedException.class)
                .hasMessage("Cross-residence access is forbidden");
    }

    private UserRepository repositoryProxy(final AtomicReference<User> savedUserRef) {
        return (UserRepository) Proxy.newProxyInstance(
                UserRepository.class.getClassLoader(),
                new Class<?>[]{UserRepository.class},
                (proxy, method, args) -> {
                    if ("save".equals(method.getName())) {
                        User user = (User) args[0];
                        savedUserRef.set(user);

                        User savedUser = new User();
                        savedUser.setId(1L);
                        savedUser.setEmail(user.getEmail());
                        savedUser.setPassword(user.getPassword());
                        savedUser.setResidenceId(user.getResidenceId());
                        savedUser.setRole(user.getRole());
                        return savedUser;
                    }
                    if ("existsByEmail".equals(method.getName())) {
                        return false;
                    }
                    if ("findAll".equals(method.getName())) {
                        return Collections.emptyList();
                    }
                    if ("findByIdAndResidence_Id".equals(method.getName())) {
                        return Optional.empty();
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

    private ResidenceService residenceServiceStub() {
        return new ResidenceService(null) {
            @Override
            public Residence getRequiredResidence(final Long residenceId) {
                Residence residence = new Residence();
                residence.setId(residenceId);
                return residence;
            }
        };
    }
}
