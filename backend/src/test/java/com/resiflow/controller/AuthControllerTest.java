package com.resiflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resiflow.dto.LoginRequest;
import com.resiflow.dto.LoginResponse;
import com.resiflow.service.AuthService;
import com.resiflow.service.InvalidCredentialsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest {

    private static final String TOKEN = "jwt-token";

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        AuthService authService = new AuthService(null, null, new BCryptPasswordEncoder()) {
            @Override
            public LoginResponse login(final LoginRequest request) {
                if (request == null || request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                    throw new IllegalArgumentException("Email must not be blank");
                }
                if ("resident@example.com".equals(request.getEmail().trim())
                        && "secret".equals(request.getPassword().trim())) {
                    return new LoginResponse(1L, "resident@example.com", 7L, TOKEN);
                }
                throw new InvalidCredentialsException("Invalid credentials");
            }
        };

        mockMvc = MockMvcBuilders.standaloneSetup(new AuthController(authService)).build();
    }

    @Test
    void loginReturnsUserDataWhenCredentialsAreValid() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("resident@example.com");
        request.setPassword("secret");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.email").value("resident@example.com"))
                .andExpect(jsonPath("$.residenceId").value(7L))
                .andExpect(jsonPath("$.token").value(TOKEN));
    }

    @Test
    void loginReturnsBadRequestWhenEmailIsBlank() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail(" ");
        request.setPassword("secret");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email must not be blank"));
    }

    @Test
    void loginReturnsUnauthorizedWhenCredentialsAreInvalid() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("resident@example.com");
        request.setPassword("wrong-password");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid credentials"));
    }
}
