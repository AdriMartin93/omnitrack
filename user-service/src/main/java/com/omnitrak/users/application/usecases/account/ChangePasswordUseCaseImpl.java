package com.omnitrak.users.application.usecases.account;

import com.omnitrak.users.domain.annotations.UseCase;
import com.omnitrak.users.domain.models.User;
import com.omnitrak.users.domain.ports.in.account.ChangePasswordUseCase;
import com.omnitrak.users.domain.ports.out.account.AccountPersistencePort;
import com.omnitrak.users.domain.ports.out.auth.PasswordEncoderPort;

import java.util.UUID;

@UseCase
public class ChangePasswordUseCaseImpl implements ChangePasswordUseCase {

    private final AccountPersistencePort accountPersistencePort;
    private final PasswordEncoderPort passwordEncoderPort;

    public ChangePasswordUseCaseImpl(AccountPersistencePort accountPersistencePort,
                                     PasswordEncoderPort passwordEncoderPort) {
        this.accountPersistencePort = accountPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public void changePassword(UUID userId, String oldPassword, String newPassword){
        User user = accountPersistencePort.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if(!passwordEncoderPort.matches(oldPassword, user.getPassword())){
            throw new IllegalArgumentException("La contraseña es incorrecta");
        }

        String encodedNewPassword = passwordEncoderPort.encode(newPassword);
        user.changePassword(encodedNewPassword);
        accountPersistencePort.save(user);
    }
}
