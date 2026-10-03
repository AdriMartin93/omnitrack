package com.omnitrak.users.application.usecases.settings;

import com.omnitrak.users.domain.annotations.UseCase;
import com.omnitrak.users.domain.models.UserSettings;
import com.omnitrak.users.domain.ports.in.settings.GetUserSettingsUseCase;
import com.omnitrak.users.domain.ports.out.settings.SettingsPersistencePort;

import java.util.UUID;

@UseCase
public class GetUserSettingsUseCaseImpl implements GetUserSettingsUseCase {

    private final SettingsPersistencePort settingsPersistencePort;

    public GetUserSettingsUseCaseImpl(SettingsPersistencePort settingsPersistencePort) {
        this.settingsPersistencePort = settingsPersistencePort;
    }

    @Override
    public UserSettings getByUserId(UUID userId){
        return settingsPersistencePort.findByUserId(userId)
                .orElseThrow(()-> new IllegalArgumentException("Configuración no encontrada"));
    }
}
