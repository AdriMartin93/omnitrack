package com.omnitrak.users.infrastructure.controllers.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(
        @NotBlank(message = "El nombre de usuario es obligatorio")
        @Size(min = 3, max = 20, message = "El usuario debe tener entre 3 y 20 carácteres")
        String username,
        @NotBlank(message = "El correo electrónico es obligatorio")
        @Email(message = "El formato de correo no es válido")
        String email,
        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 6, message = "La contraseña debe tener almenso 6 carácteres")
        String password) { }
