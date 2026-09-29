package com.omnitrak.users.infrastructure.controllers.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordRequestDto(
        @NotBlank(message = "El correo electrónico es obligatrorio")
        @Email(message = "El formato de correo no es válido")
        String email) { }
