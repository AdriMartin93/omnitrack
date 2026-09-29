package com.omnitrak.users.infrastructure.controllers.auth.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequestDto(
        @NotBlank(message = "El token es obligatorio")
        String token,
        @NotBlank(message = "La nueva contraseña es obligatoria")
        @Size(min = 6, message = "La contraseña debe contener almenos 6 carácteres")
        String newPassword) { }
