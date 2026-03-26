package com.resiflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resiflow.dto.CreateUserRequest;
import com.resiflow.entity.User;
import com.resiflow.entity.UserRole;
import com.resiflow.security.AuthenticatedUser;
import com.resiflow.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        UserService userService = new UserService(null, new BCryptPasswordEncoder(), null) {
            @Override
            public User createResidenceUser(final CreateUserRequest request, final AuthenticatedUser authenticatedUser) {
                if (request == null || request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                    throw new IllegalArgumentException("Email must not be blank");
                }

                User user = new User();
                user.setId(1L);
                user.setEmail(request.getEmail().trim());
                user.setPassword(request.getPassword().trim());
                user.setResidenceId(authenticatedUser.residenceId());
                user.setRole(UserRole.USER);
                return user;
            }
        };

        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createUserReturnsCreatedUser() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("resident@example.com");
        request.setPassword("secret");

        mockMvc.perform(post("/users")
                        .with(authenticatedUser(new AuthenticatedUser(9L, "admin@example.com", 7L, UserRole.ADMIN)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("resident@example.com"))
                .andExpect(jsonPath("$.residenceId").value(7L))
                .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    void createUserReturnsBadRequestWhenServiceRejectsRequest() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail(" ");
        request.setPassword("secret");

        mockMvc.perform(post("/users")
                        .with(authenticatedUser(new AuthenticatedUser(9L, "admin@example.com", 7L, UserRole.ADMIN)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email must not be blank"));
    }

    private RequestPostProcessor authenticatedUser(final AuthenticatedUser authenticatedUser) {
        return request -> {
            Authentication authentication = new UsernamePasswordAuthenticationToken(authenticatedUser, null);
            request.setUserPrincipal(authentication);
            return request;
        };
    }
}
