package com.omnitrak.users.domain.ports.out.auth;

import com.omnitrak.users.domain.models.AuthTokens;
import com.omnitrak.users.domain.models.User;

import java.util.UUID;

public interface TokenProviderPort {

    AuthTokens generateToken(User user);

    boolean validateToken(String token);

    AuthTokens refreshToken(String refreshToken);

    String extractUsername(String token);

    UUID extractUserId(String token);
}
