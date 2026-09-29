package com.omnitrak.users.domain.ports.in.settings;

import com.omnitrak.users.domain.models.UserSettings;

import java.util.UUID;

public interface GetUserSettingsUseCase {
    UserSettings getByUserId(UUID userId);
}
