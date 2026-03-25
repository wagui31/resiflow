package com.resiflow.service;

import com.resiflow.dto.CreateUserRequest;
import com.resiflow.entity.User;
import com.resiflow.repository.UserRepository;
import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceTest {

    @Test
    void createUserSavesUserFromRequest() {
        AtomicReference<User> savedUserRef = new AtomicReference<>();
        UserRepository userRepository = repositoryProxy(savedUserRef);
        UserService userService = new UserService(userRepository);

        CreateUserRequest request = new CreateUserRequest();
        request.setEmail(" resident@example.com ");
        request.setPassword(" secret ");
        request.setResidenceId(7L);

        User result = userService.createUser(request);

        User userToSave = savedUserRef.get();
        assertThat(userToSave.getEmail()).isEqualTo("resident@example.com");
        assertThat(userToSave.getPassword()).isEqualTo("secret");
        assertThat(userToSave.getResidenceId()).isEqualTo(7L);
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void createUserRejectsBlankEmail() {
        UserService userService = new UserService(repositoryProxy(new AtomicReference<>()));

        CreateUserRequest request = new CreateUserRequest();
        request.setEmail(" ");
        request.setPassword("secret");
        request.setResidenceId(7L);

        assertThatThrownBy(() -> userService.createUser(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email must not be blank");
    }

    @Test
    void createUserRejectsNullResidenceId() {
        UserService userService = new UserService(repositoryProxy(new AtomicReference<>()));

        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("resident@example.com");
        request.setPassword("secret");

        assertThatThrownBy(() -> userService.createUser(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Residence ID must not be null");
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
                        return savedUser;
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
