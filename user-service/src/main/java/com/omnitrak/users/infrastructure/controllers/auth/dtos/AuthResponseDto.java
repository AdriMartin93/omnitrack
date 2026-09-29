package com.omnitrak.users.infrastructure.controllers.auth.dtos;

public record AuthResponseDto (
        String accesToken,
        String refreshToken,
        String tokenType
) {
    public static AuthResponseDto of(String accesToken, String refreshToken) {
        return new AuthResponseDto(accesToken, refreshToken, "Bearer");
    }
}
