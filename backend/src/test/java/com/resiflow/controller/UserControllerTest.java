package com.resiflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resiflow.dto.CreateUserRequest;
import com.resiflow.entity.User;
import com.resiflow.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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
        UserService userService = new UserService(null) {
            @Override
            public User createUser(final CreateUserRequest request) {
                if (request == null || request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                    throw new IllegalArgumentException("Email must not be blank");
                }

                User user = new User();
                user.setId(1L);
                user.setEmail(request.getEmail().trim());
                user.setPassword(request.getPassword().trim());
                user.setResidenceId(request.getResidenceId());
                return user;
            }
        };

        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService)).build();
    }

    @Test
    void createUserReturnsCreatedUser() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("resident@example.com");
        request.setPassword("secret");
        request.setResidenceId(7L);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("resident@example.com"))
                .andExpect(jsonPath("$.password").value("secret"))
                .andExpect(jsonPath("$.residenceId").value(7L));
    }

    @Test
    void createUserReturnsBadRequestWhenServiceRejectsRequest() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail(" ");
        request.setPassword("secret");
        request.setResidenceId(7L);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email must not be blank"));
    }
}
