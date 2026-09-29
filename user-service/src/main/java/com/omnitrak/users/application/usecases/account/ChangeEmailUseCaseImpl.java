package com.omnitrak.users.application.usecases.account;

import com.omnitrak.users.domain.models.User;
import com.omnitrak.users.domain.ports.in.account.ChangeEmailUseCase;
import com.omnitrak.users.domain.ports.out.account.AccountPersistencePort;
import com.omnitrak.users.domain.ports.out.auth.PasswordEncoderPort;
import java.util.UUID;

public class ChangeEmailUseCaseImpl implements ChangeEmailUseCase {

    private final AccountPersistencePort accountPersistencePort;
    private final PasswordEncoderPort passwordEncoderPort;

    public ChangeEmailUseCaseImpl(AccountPersistencePort accountPersistencePort, PasswordEncoderPort passwordEncoderPort) {
        this.accountPersistencePort = accountPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public void changeEmail(UUID userId, String newEmail, String password){
        User user = accountPersistencePort.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if(!passwordEncoderPort.matches(password, user.getPassword())){
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        user.changeEmail(newEmail);
        accountPersistencePort.save(user);
    }
}
