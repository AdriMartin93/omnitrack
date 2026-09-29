package com.omnitrak.users.infrastructure.controllers.settings.dtos;

import jakarta.validation.constraints.NotNull;

public record UpdateProfileVisibilityRequestDto(
        @NotNull(message = "El estado del perfil es obligatorio.")
        Boolean profileVisible
) { }
