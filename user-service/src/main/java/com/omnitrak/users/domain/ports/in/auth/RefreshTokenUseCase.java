package com.omnitrak.users.domain.ports.in.auth;

import com.omnitrak.users.domain.models.AuthTokens;

public interface RefreshTokenUseCase {
    AuthTokens refresh(String refreshToken);

}
