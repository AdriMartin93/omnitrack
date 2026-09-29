package com.omnitrak.users.infrastructure.controllers.profile.dtos;

import jakarta.validation.constraints.NotBlank;

public record UpdateBannerRequestDto(
        @NotBlank(message = "La URL del banner es obligatoria.")
        String newBanner) { }
