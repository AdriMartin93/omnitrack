package com.omnitrak.users.infrastructure.controllers.auth.dtos;

import com.omnitrak.users.domain.models.User;

import java.util.UUID;

public record UserResponseDto (
        UUID id,
        String username,
        String email,
        String role,
        boolean active
) {
    public static UserResponseDto fromDomain(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.isActive()
        );
    }
}
