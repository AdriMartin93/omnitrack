package com.omnitrak.users.application.services;

import com.omnitrak.users.domain.models.AuthTokens;
import com.omnitrak.users.domain.models.User;
import com.omnitrak.users.domain.ports.in.auth.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final LogoutUserUseCase logoutUserUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    private final RequestPasswordUseCase requestPasswordUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;



    public AuthTokens login(String username, String password) {
        return authenticateUserUseCase.login(username, password);
    }


    public void logout(UUID userId, String refreshToken) {
        logoutUserUseCase.logout(userId, refreshToken);
    }


    public AuthTokens refresh(String refreshToken) {
        return refreshTokenUseCase.refresh(refreshToken);
    }


    public User registerUser(String username, String email, String password) {
        return registerUserUseCase.registerUser(username, email, password);
    }


    public void requestPasswordReset(String email) {
        requestPasswordUseCase.requestPasswordReset(email);
    }


    public void resetPassword(String token, String newPassword) {
        resetPasswordUseCase.resetPassword(token, newPassword);
    }
}
