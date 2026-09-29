package com.omnitrak.users.application.usecases.profile;

import com.omnitrak.users.domain.models.User;
import com.omnitrak.users.domain.ports.in.profile.UpdateBioUseCase;
import com.omnitrak.users.domain.ports.out.profile.ProfilePersistencePort;

import java.util.UUID;

public class UpdateBioUseCaseImpl implements UpdateBioUseCase {

    private final ProfilePersistencePort profilePersistencePort;

    public UpdateBioUseCaseImpl(ProfilePersistencePort profilePersistencePort) {
        this.profilePersistencePort = profilePersistencePort;
    }

    @Override
    public void updateBio(UUID userId, String newBio){

        User user = profilePersistencePort.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        user.updateBio(newBio);
        profilePersistencePort.save(user);
    }
}
