package com.omnitrak.users.infrastructure.controllers.profile.dtos;


import jakarta.validation.constraints.Size;

public record UpdateBioRequestDto(
        @Size(message = "La biografía no puede exceder los 500 caracteres.")
        String newBio) { }
