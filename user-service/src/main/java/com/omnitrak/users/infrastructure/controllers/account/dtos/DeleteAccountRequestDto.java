package com.omnitrak.users.infrastructure.controllers.account.dtos;

import jakarta.validation.constraints.NotBlank;

public record DeleteAccountRequestDto(
        @NotBlank(message = "Debes introducir tu contraseña para confirmar la eliminación de la cuenta.")
        String password) { }
