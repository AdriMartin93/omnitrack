package com.omnitrak.users.infrastructure.controllers.settings.dtos;

import jakarta.validation.constraints.NotNull;

public record UpdateListVisibilityRequestDto(
        @NotNull(message = "La visibilidad es obligatoria")
        Boolean isPrivateList
) {
}
