package com.omnitrak.users.infrastructure.controllers.account.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ChangeEmailRequestDto(
        @NotBlank(message = "El nuevo correo electrónico és obligatorio.")
        @Email(message = "El formato de correo electrónico no es correcto.")
        String newEmail,
        @NotBlank(message = "La contraseña és obligatoria.")
        String password) { }
