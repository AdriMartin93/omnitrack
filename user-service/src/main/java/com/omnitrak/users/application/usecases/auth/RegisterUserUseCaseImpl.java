package com.omnitrak.users.application.usecases.auth;

import com.omnitrak.users.domain.models.User;
import com.omnitrak.users.domain.ports.in.auth.RegisterUserUseCase;
import com.omnitrak.users.domain.ports.out.account.AccountPersistencePort;
import com.omnitrak.users.domain.ports.out.auth.ActivationTokenPort;
import com.omnitrak.users.domain.ports.out.auth.EmailNotificationPort;
import com.omnitrak.users.domain.ports.out.auth.PasswordEncoderPort;

import java.time.LocalDateTime;
import java.util.UUID;

public class RegisterUserUseCaseImpl implements RegisterUserUseCase {

    private static final long ACTIVATION_TOKEN_TTL_MINUTES = 1440;

    private final AccountPersistencePort accountPersistencePort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final ActivationTokenPort activationTokenPort;
    private final EmailNotificationPort emailNotificationPort;

    public RegisterUserUseCaseImpl(
            AccountPersistencePort accountPersistencePort,
            PasswordEncoderPort passwordEncoderPort,
            ActivationTokenPort activationTokenPort,
            EmailNotificationPort emailNotificationPort) {
        this.accountPersistencePort = accountPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.activationTokenPort = activationTokenPort;
        this.emailNotificationPort = emailNotificationPort;
    }

    @Override
    public User registerUser(String username, String email, String rawPassword){
        if(accountPersistencePort.existsByUsername(username)){
            throw new IllegalArgumentException("El username ya existe");
        }
        if(accountPersistencePort.existsByEmail(email)){
            throw new IllegalArgumentException("El email ya existe");
        }

        String encodedPassword = passwordEncoderPort.encode(rawPassword);
        User newUser = new User(
                UUID.randomUUID(),
                username,
                encodedPassword,
                email,
                "USER",
                null,
                null,
                null,
                false,
                null,
                LocalDateTime.now());
        User savedUser = accountPersistencePort.save(newUser);

        String activationToken = UUID.randomUUID().toString();
        activationTokenPort.saveActivationToken(username, activationToken, ACTIVATION_TOKEN_TTL_MINUTES);
        emailNotificationPort.sendAccountActivationEmail(email, activationToken);

        return savedUser;
    }
}
