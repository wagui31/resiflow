package com.resiflow.controller;

import com.resiflow.dto.CreateAdminRequest;
import com.resiflow.dto.CreateUserRequest;
import com.resiflow.dto.CreateUserResponse;
import com.resiflow.dto.UserResponse;
import com.resiflow.entity.User;
import com.resiflow.security.AuthenticatedUser;
import com.resiflow.service.UserService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/users", "/api/users"})
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateUserResponse> createUser(
            @RequestBody final CreateUserRequest request,
            final Authentication authentication
    ) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();
        User createdUser = userService.createResidenceUser(request, authenticatedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(CreateUserResponse.fromUser(createdUser));
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<CreateUserResponse> createAdmin(@RequestBody final CreateAdminRequest request) {
        User createdUser = userService.createAdmin(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(CreateUserResponse.fromUser(createdUser));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<List<UserResponse>> getUsers(final Authentication authentication) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();
        List<UserResponse> responses = userService.getUsers(authenticatedUser).stream()
                .map(UserResponse::fromUser)
                .toList();
        return ResponseEntity.ok(responses);
    }
}
