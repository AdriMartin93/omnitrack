package com.omnitrak.users.application.usecases.profile;

import com.omnitrak.users.domain.annotations.UseCase;
import com.omnitrak.users.domain.models.User;
import com.omnitrak.users.domain.ports.in.profile.UpdateUsernameUseCase;
import com.omnitrak.users.domain.ports.out.profile.ProfilePersistencePort;

import java.util.UUID;

@UseCase
public class UpdateUsernameUseCaseImpl implements UpdateUsernameUseCase {

    private final ProfilePersistencePort profilePersistencePort;

    public UpdateUsernameUseCaseImpl(ProfilePersistencePort profilePersistencePort) {
        this.profilePersistencePort = profilePersistencePort;
    }

    @Override
    public void updateUsername(UUID userId, String newUsername){

        User user = profilePersistencePort.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("Usuario no encontrado"));

        user.updateUsername(newUsername);
        profilePersistencePort.save(user);
    }
}
