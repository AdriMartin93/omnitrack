package com.omnitrak.users.application.usecases.auth;

import com.omnitrak.users.domain.ports.in.auth.LogoutUserUseCase;
import com.omnitrak.users.domain.ports.out.auth.TokenBlacklistPort;

import java.util.UUID;

public class LogoutUserUseCaseImpl implements LogoutUserUseCase {

    private final TokenBlacklistPort tokenBlacklistPort;

    public LogoutUserUseCaseImpl(TokenBlacklistPort tokenBlacklistPort) {
        this.tokenBlacklistPort = tokenBlacklistPort;
    }

    @Override
    public void logout(UUID userId, String refreshToken){
        tokenBlacklistPort.revokeToken(refreshToken);
    }
}
