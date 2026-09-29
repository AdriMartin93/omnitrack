package com.omnitrak.users.infrastructure.controllers.auth.dtos;

import jakarta.validation.constraints.NotBlank;

public record LogoutRequestDto(
        @NotBlank(message = "Refresh token obligatorio.")
        String refreshToken) {
}
