package com.omnitrak.users.domain.ports.out.auth;

import java.util.UUID;

public interface TokenBlacklistPort {

    void revokeToken(String token);
    void revokeAllUserTokens(UUID userId);
    boolean isTokenRevoked(String token);
}
