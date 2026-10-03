package com.omnitrak.users.application.usecases.auth;

import com.omnitrak.users.domain.annotations.UseCase;
import com.omnitrak.users.domain.models.User;
import com.omnitrak.users.domain.ports.in.auth.RequestPasswordUseCase;
import com.omnitrak.users.domain.ports.out.account.AccountPersistencePort;
import com.omnitrak.users.domain.ports.out.auth.EmailNotificationPort;
import com.omnitrak.users.domain.ports.out.auth.PasswordResetTokenPort;

import java.util.Optional;
import java.util.UUID;

@UseCase
public class RequestPasswordUseCaseImpl implements RequestPasswordUseCase {

    private static final long DEFAULT_TTL_MINUTES = 15;
    private final AccountPersistencePort accountPersistencePort;
    private final PasswordResetTokenPort passwordResetTokenPort;
    private final EmailNotificationPort emailNotificationPort;

    public RequestPasswordUseCaseImpl(
            AccountPersistencePort accountPersistencePort,
            PasswordResetTokenPort passwordResetTokenPort,
            EmailNotificationPort emailNotificationPort) {
        this.accountPersistencePort = accountPersistencePort;
        this.passwordResetTokenPort = passwordResetTokenPort;
        this.emailNotificationPort = emailNotificationPort;
    }


    public void requestPasswordReset(String email){
        Optional<User> optionalUser = accountPersistencePort.findByEmail(email);

        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            String resetToken = UUID.randomUUID().toString();

            passwordResetTokenPort.saveResetToken(email, resetToken, DEFAULT_TTL_MINUTES);
            emailNotificationPort.sendPasswordResetEmail(user.getEmail(), resetToken);
        }
    }
}
