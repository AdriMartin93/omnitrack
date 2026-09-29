package com.omnitrak.users.application.usecases.auth;

import com.omnitrak.users.domain.models.AuthTokens;
import com.omnitrak.users.domain.ports.in.auth.RefreshTokenUseCase;
import com.omnitrak.users.domain.ports.out.auth.TokenProviderPort;


public class RefreshTokenUseCaseImpl implements RefreshTokenUseCase {

    private final TokenProviderPort tokenProviderPort;

    public RefreshTokenUseCaseImpl(TokenProviderPort tokenProviderPort) {
        this.tokenProviderPort = tokenProviderPort;
    }

    @Override
    public AuthTokens refresh(String refreshToken) {
        return tokenProviderPort.refreshToken(refreshToken);
    }
}
