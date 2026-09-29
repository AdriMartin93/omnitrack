package com.omnitrak.users.application.usecases.profile;

import com.omnitrak.users.domain.models.User;
import com.omnitrak.users.domain.ports.in.profile.UpdateBannerUseCase;
import com.omnitrak.users.domain.ports.out.profile.ProfilePersistencePort;

import java.util.UUID;

public class UpdateBannerUseCaseImpl implements UpdateBannerUseCase {

    private final ProfilePersistencePort profilePersistencePort;

    public UpdateBannerUseCaseImpl(ProfilePersistencePort profilePersistencePort) {
        this.profilePersistencePort = profilePersistencePort;
    }

    @Override
    public void updateBanner(UUID userId, String newBanner){

        User user = profilePersistencePort.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("Usuario no encontrado"));

        user.updateBanner(newBanner);
        profilePersistencePort.save(user);
    }
}
