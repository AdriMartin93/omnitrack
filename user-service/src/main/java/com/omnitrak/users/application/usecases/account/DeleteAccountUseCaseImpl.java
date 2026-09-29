package com.omnitrak.users.application.usecases.account;

import com.omnitrak.users.domain.models.User;
import com.omnitrak.users.domain.ports.in.account.DeleteAccountUseCase;
import com.omnitrak.users.domain.ports.out.account.AccountPersistencePort;
import com.omnitrak.users.domain.ports.out.auth.PasswordEncoderPort;

import java.util.UUID;

public class DeleteAccountUseCaseImpl implements DeleteAccountUseCase {

    private final AccountPersistencePort accountPersistencePort;
    private final PasswordEncoderPort passwordEncoderPort;

    public DeleteAccountUseCaseImpl(AccountPersistencePort accountPersistencePort, PasswordEncoderPort passwordEncoderPort) {
        this.accountPersistencePort = accountPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

   public void deleteAccount(UUID userId, String password){
        User user = accountPersistencePort.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("Usuario no encontrado"));

        if(!passwordEncoderPort.matches(password, user.getPassword())){
            throw new IllegalArgumentException("Credenciales incorrectas");
        }

        user.deleteAccount();
        accountPersistencePort.save(user);
   }
}
