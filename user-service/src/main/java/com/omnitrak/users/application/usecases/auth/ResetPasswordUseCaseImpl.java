package com.omnitrak.users.application.usecases.auth;

import com.omnitrak.users.domain.annotations.UseCase;
import com.omnitrak.users.domain.models.User;
import com.omnitrak.users.domain.ports.in.auth.ResetPasswordUseCase;
import com.omnitrak.users.domain.ports.out.account.AccountPersistencePort;
import com.omnitrak.users.domain.ports.out.auth.PasswordEncoderPort;
import com.omnitrak.users.domain.ports.out.auth.PasswordResetTokenPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@UseCase
public class ResetPasswordUseCaseImpl implements ResetPasswordUseCase {

    private final PasswordEncoderPort passwordEncoderPort;
    private final AccountPersistencePort accountPersistencePort;
    private final PasswordResetTokenPort passwordResetTokenPort;

    public ResetPasswordUseCaseImpl(PasswordEncoderPort passwordEncoderPort, AccountPersistencePort accountPersistencePort,
                                    PasswordResetTokenPort passwordResetTokenPort) {

        this.passwordEncoderPort = passwordEncoderPort;
        this.accountPersistencePort = accountPersistencePort;
        this.passwordResetTokenPort = passwordResetTokenPort;
    }

    @Override
    public void resetPassword(String token, String newPassword){

        String email = passwordResetTokenPort.getEmailByResetToken(token)
                .orElseThrow(()-> new IllegalArgumentException("Invalid token"));

        User user = accountPersistencePort.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("Invalid email"));

        String encodedPassword = passwordEncoderPort.encode(newPassword);

        user.changePassword(encodedPassword);

        accountPersistencePort.save(user);
        passwordResetTokenPort.deleteResetToken(token);

    }
}
