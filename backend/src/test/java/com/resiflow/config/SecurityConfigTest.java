package com.resiflow.config;

import com.resiflow.dto.CreateUserRequest;
import com.resiflow.dto.LoginRequest;
import com.resiflow.dto.LoginResponse;
import com.resiflow.controller.AuthController;
import com.resiflow.controller.HealthController;
import com.resiflow.controller.UserController;
import com.resiflow.entity.User;
import com.resiflow.security.JwtAuthenticationFilter;
import com.resiflow.security.JwtProperties;
import com.resiflow.security.JwtService;
import com.resiflow.security.RestAuthenticationEntryPoint;
import com.resiflow.service.AuthService;
import com.resiflow.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {
        HealthController.class,
        AuthController.class,
        UserController.class,
        SecurityConfigTest.TestProtectedController.class
})
@Import({
        SecurityConfig.class,
        JwtService.class,
        JwtAuthenticationFilter.class,
        RestAuthenticationEntryPoint.class,
        SecurityConfigTest.TestProtectedController.class,
        SecurityConfigTest.SecurityTestConfiguration.class
})
class SecurityConfigTest {

    private static final String SECRET = "Zm9yLXRlc3RzLW9ubHktcmVzaWZsb3ctand0LXNlY3JldC1rZXktMzItYnl0ZXM=";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private UserService userService;

    @Test
    void healthEndpointIsPublic() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("OK"));
    }

    @Test
    void loginEndpointIsPublic() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("resident@example.com");
        request.setPassword("secret");

        when(authService.login(any(LoginRequest.class)))
                .thenReturn(new LoginResponse(4L, "resident@example.com", 12L, "jwt-token"));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email":"resident@example.com","password":"secret"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token"));
    }

    @Test
    void createUserEndpointIsPublic() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("resident@example.com");
        user.setPassword(passwordEncoder.encode("secret"));
        user.setResidenceId(7L);

        when(userService.createUser(any(CreateUserRequest.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email":"resident@example.com","password":"secret","residenceId":7}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void protectedEndpointReturnsUnauthorizedWithoutToken() throws Exception {
        mockMvc.perform(get("/protected"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void protectedEndpointAcceptsValidBearerToken() throws Exception {
        User user = new User();
        user.setId(4L);
        user.setEmail("resident@example.com");
        user.setResidenceId(12L);

        String token = jwtService.generateToken(user);

        mockMvc.perform(get("/protected")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("protected"));
    }

    @RestController
    static class TestProtectedController {

        @GetMapping("/protected")
        String protectedEndpoint() {
            return "protected";
        }
    }

    @TestConfiguration
    @EnableConfigurationProperties(JwtProperties.class)
    static class SecurityTestConfiguration {
    }
}
