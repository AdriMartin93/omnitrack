package com.omnitrak.users.application.usecases.auth;

import com.omnitrak.users.domain.annotations.UseCase;
import com.omnitrak.users.domain.models.AuthTokens;
import com.omnitrak.users.domain.models.User;
import com.omnitrak.users.domain.ports.in.auth.AuthenticateUserUseCase;
import com.omnitrak.users.domain.ports.out.auth.AuthUserPersistencePort;
import com.omnitrak.users.domain.ports.out.auth.PasswordEncoderPort;
import com.omnitrak.users.domain.ports.out.auth.TokenProviderPort;

@UseCase
public class AuthenticateUserUseCaseImpl implements AuthenticateUserUseCase {

    private final AuthUserPersistencePort authUserPersistencePort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final TokenProviderPort tokenProviderPort;

    public AuthenticateUserUseCaseImpl(AuthUserPersistencePort authUserPersistencePort, PasswordEncoderPort passwordEncoderPort, TokenProviderPort tokenProviderPort){
        this.authUserPersistencePort = authUserPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.tokenProviderPort = tokenProviderPort;
    }

    @Override
    public AuthTokens login(String username, String rawPassword){

        return authUserPersistencePort.findByUsername(username)
                .filter(user ->passwordEncoderPort.matches(rawPassword, user.getPassword()))
                .filter(User::isActive)
                .map(tokenProviderPort::generateToken)
                .orElseThrow(() -> new IllegalArgumentException("Credenciales invalidas o usuario no activado"));
    }


}


