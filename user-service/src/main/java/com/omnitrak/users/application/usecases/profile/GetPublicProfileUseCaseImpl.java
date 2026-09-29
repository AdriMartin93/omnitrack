package com.omnitrak.users.application.usecases.profile;

import com.omnitrak.users.domain.factory.PublicProfileFactory;
import com.omnitrak.users.domain.models.PublicProfile;
import com.omnitrak.users.domain.models.User;
import com.omnitrak.users.domain.models.UserSettings;
import com.omnitrak.users.domain.ports.in.profile.GetPublicProfileUseCase;
import com.omnitrak.users.domain.ports.out.profile.ProfilePersistencePort;
import com.omnitrak.users.domain.ports.out.settings.SettingsPersistencePort;

import java.util.UUID;

public class GetPublicProfileUseCaseImpl implements GetPublicProfileUseCase {

    private final ProfilePersistencePort profilePersistencePort;
    private final SettingsPersistencePort settingsPersistencePort;

    public GetPublicProfileUseCaseImpl(ProfilePersistencePort profilePersistencePort, SettingsPersistencePort settingsPersistencePort) {
        this.profilePersistencePort = profilePersistencePort;
        this.settingsPersistencePort = settingsPersistencePort;
    }

    @Override
    public PublicProfile getById(UUID userId){
        User user = profilePersistencePort.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("Usuario no encontrado"));

        UserSettings settings = settingsPersistencePort.findByUserId(userId)
                .orElse(null);

        return PublicProfileFactory.create(user, settings);
    }

    @Override
    public PublicProfile getByUsername(String username) {

        User user = profilePersistencePort.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        UserSettings settings = settingsPersistencePort.findByUserId(user.getId())
                .orElse(null);

        return PublicProfileFactory.create(user, settings);
    }
}
