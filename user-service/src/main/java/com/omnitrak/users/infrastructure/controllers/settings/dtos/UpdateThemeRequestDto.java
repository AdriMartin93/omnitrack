package com.omnitrak.users.infrastructure.controllers.settings.dtos;

import com.omnitrak.users.domain.models.enums.Theme;
import jakarta.validation.constraints.NotNull;

public record UpdateThemeRequestDto (
        @NotNull(message = "El tema es oblgatorio")
        Theme theme){ }
