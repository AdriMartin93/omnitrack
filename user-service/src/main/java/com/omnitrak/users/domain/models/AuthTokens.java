package com.omnitrak.users.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class AuthTokens {

    private final String accessToken;
    private final String refreshToken;
    private final String tokenType;
    private final long expiresIn;


    public AuthTokens(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = "Bearer";
        this.expiresIn = 3600; // 1 hora por defecto
    }
}
