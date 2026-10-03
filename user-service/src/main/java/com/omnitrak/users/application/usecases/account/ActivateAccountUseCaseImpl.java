package com.omnitrak.users.application.usecases.account;

import com.omnitrak.users.domain.annotations.UseCase;
import com.omnitrak.users.domain.models.User;
import com.omnitrak.users.domain.ports.in.auth.ActivateAccountUseCase;
import com.omnitrak.users.domain.ports.out.account.AccountPersistencePort;
import com.omnitrak.users.domain.ports.out.auth.ActivationTokenPort;

@UseCase
public class ActivateAccountUseCaseImpl implements ActivateAccountUseCase {

    private final ActivationTokenPort activationTokenPort;
    private final AccountPersistencePort accountPersistencePort;

    public ActivateAccountUseCaseImpl(ActivationTokenPort activationTokenPort, AccountPersistencePort accountPersistencePort) {
        this.activationTokenPort = activationTokenPort;
        this.accountPersistencePort = accountPersistencePort;
    }

    @Override
    public void activate(String token){
        String username = activationTokenPort.getUsernameByActivationToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token invalido"));

        User user = accountPersistencePort.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if(user.isActive()){
            activationTokenPort.deleteActivationToken(token);
            return;
        }


        User activatedUser = new User(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRole(),
                null,
                null,
                null,
                true,
                user.getLastLoginDate(),
                user.getCreationDate()
        );

        accountPersistencePort.save(activatedUser);
        activationTokenPort.deleteActivationToken(token);
    }

}
