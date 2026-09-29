package com.omnitrak.users.domain.ports.out.settings;

import com.omnitrak.users.domain.models.UserSettings;

import java.util.Optional;
import java.util.UUID;

public interface SettingsPersistencePort {

    Optional<UserSettings> findByUserId(UUID userId);

    UserSettings save(UserSettings settings);
}
