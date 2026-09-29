package com.omnitrak.users.infrastructure.controllers.account.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequestDto(
        @NotBlank(message = "Es obligatorio introducir la contraseña actual.")
        String password,
        @NotBlank(message = "Debes introducir la nueva contraseña.")
        @Size(min = 6, message = "La contraseña debe contener mínimo 6 carácteres.")
        String newPassword) { }
