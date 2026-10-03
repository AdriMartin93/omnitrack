package com.omnitrak.users.application.usecases.profile;

import com.omnitrak.users.domain.annotations.UseCase;
import com.omnitrak.users.domain.models.User;
import com.omnitrak.users.domain.ports.in.profile.UpdateAvatarUseCase;
import com.omnitrak.users.domain.ports.out.profile.ProfilePersistencePort;

import java.util.UUID;

@UseCase
public class UpdateAvatarUseCaseImpl implements UpdateAvatarUseCase {

    public final ProfilePersistencePort profilePersistencePort;

    public UpdateAvatarUseCaseImpl(ProfilePersistencePort profilePersistencePort) {
        this.profilePersistencePort = profilePersistencePort;
    }

    @Override
    public void updateAvatar(UUID userId, String newAvatar) {
        User user = profilePersistencePort.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        user.updateAvatar(newAvatar);

        profilePersistencePort.save(user);
    }


}
