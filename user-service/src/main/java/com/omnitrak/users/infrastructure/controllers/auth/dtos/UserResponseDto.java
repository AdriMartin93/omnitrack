package com.omnitrak.users.infrastructure.controllers.auth.dtos;

import com.omnitrak.users.domain.models.User;
import com.omnitrak.users.domain.models.enums.Role;

import java.util.UUID;

public record UserResponseDto (
        UUID id,
        String username,
        String email,
        Role role,
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
