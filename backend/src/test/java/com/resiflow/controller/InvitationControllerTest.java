package com.resiflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resiflow.dto.CreateInvitationRequest;
import com.resiflow.dto.InvitationResponse;
import com.resiflow.entity.UserRole;
import com.resiflow.security.AuthenticatedUser;
import com.resiflow.service.InvitationService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class InvitationControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        InvitationService invitationService = new InvitationService(null) {
            @Override
            public InvitationResponse createInvitation(
                    final CreateInvitationRequest request,
                    final AuthenticatedUser authenticatedUser
            ) {
                if (request == null || request.getTargetValue() == null || request.getTargetValue().trim().isEmpty()) {
                    throw new IllegalArgumentException("Target value must not be blank");
                }

                return new InvitationResponse(
                        1L,
                        authenticatedUser.residenceId(),
                        request.getTargetValue().trim(),
                        "generated-token",
                        "ACTIVE",
                        request.getExpiresAt(),
                        authenticatedUser.userId(),
                        LocalDateTime.of(2026, 3, 26, 10, 0)
                );
            }
        };

        mockMvc = MockMvcBuilders.standaloneSetup(new InvitationController(invitationService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createInvitationReturnsCreatedInvitation() throws Exception {
        CreateInvitationRequest request = new CreateInvitationRequest();
        request.setTargetValue("resident@example.com");
        request.setExpiresAt(LocalDateTime.of(2026, 4, 1, 12, 0));

        mockMvc.perform(post("/invitations")
                        .with(authenticatedUser(new AuthenticatedUser(9L, "admin@example.com", 7L, UserRole.ADMIN)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.residenceId").value(7L))
                .andExpect(jsonPath("$.targetValue").value("resident@example.com"))
                .andExpect(jsonPath("$.token").value("generated-token"))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.createdBy").value(9L));
    }

    @Test
    void createInvitationReturnsBadRequestWhenServiceRejectsRequest() throws Exception {
        CreateInvitationRequest request = new CreateInvitationRequest();
        request.setTargetValue(" ");
        request.setExpiresAt(LocalDateTime.of(2026, 4, 1, 12, 0));

        mockMvc.perform(post("/invitations")
                        .with(authenticatedUser(new AuthenticatedUser(9L, "admin@example.com", 7L, UserRole.ADMIN)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Target value must not be blank"));
    }

    private RequestPostProcessor authenticatedUser(final AuthenticatedUser authenticatedUser) {
        return request -> {
            Authentication authentication = new UsernamePasswordAuthenticationToken(authenticatedUser, null);
            request.setUserPrincipal(authentication);
            return request;
        };
    }
}
