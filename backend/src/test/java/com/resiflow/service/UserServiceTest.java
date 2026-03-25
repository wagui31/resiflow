package com.resiflow.service;

import com.resiflow.dto.CreateUserRequest;
import com.resiflow.entity.User;
import com.resiflow.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUserSavesUserFromRequest() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail(" resident@example.com ");
        request.setPassword(" secret ");
        request.setResidenceId(7L);

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail("resident@example.com");
        savedUser.setPassword("secret");
        savedUser.setResidenceId(7L);

        when(userRepository.save(org.mockito.ArgumentMatchers.any(User.class))).thenReturn(savedUser);

        User result = userService.createUser(request);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User userToSave = userCaptor.getValue();
        assertThat(userToSave.getEmail()).isEqualTo("resident@example.com");
        assertThat(userToSave.getPassword()).isEqualTo("secret");
        assertThat(userToSave.getResidenceId()).isEqualTo(7L);
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void createUserRejectsBlankEmail() {
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
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("resident@example.com");
        request.setPassword("secret");

        assertThatThrownBy(() -> userService.createUser(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Residence ID must not be null");
    }
}
