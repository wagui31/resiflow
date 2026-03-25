package com.resiflow.security;

public record AuthenticatedUser(Long userId, String email, Long residenceId) {
}
