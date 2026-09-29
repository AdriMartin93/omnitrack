package com.omnitrak.users.infrastructure.controllers.profile.dtos;

import jakarta.validation.constraints.NotBlank;

public record UpdateAvatarRequestDto(
        @NotBlank(message = "La URL del avatar es obligatoria.")
        String newAvatar) {
}
