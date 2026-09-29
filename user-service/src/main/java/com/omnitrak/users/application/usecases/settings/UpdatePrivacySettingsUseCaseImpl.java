package com.omnitrak.users.application.usecases.settings;

import com.omnitrak.users.domain.models.UserSettings;
import com.omnitrak.users.domain.ports.in.settings.UpdatePrivacySettingsUseCase;
import com.omnitrak.users.domain.ports.out.settings.SettingsPersistencePort;

import java.util.UUID;

public class UpdatePrivacySettingsUseCaseImpl implements UpdatePrivacySettingsUseCase {

    private final SettingsPersistencePort settingsPersistencePort;

    public UpdatePrivacySettingsUseCaseImpl(SettingsPersistencePort settingsPersistencePort) {
        this.settingsPersistencePort = settingsPersistencePort;
    }

    @Override
    public void updateProfileVisibility(UUID userId, boolean profilePrivate){
        UserSettings settings = settingsPersistencePort.findByUserId(userId)
                .orElseThrow(()-> new IllegalArgumentException("Configuración no encontrada"));

        settings.updateProfileVisibility(profilePrivate);
        settingsPersistencePort.save(settings);
    }

    @Override
    public void updateListVisibility(UUID userId, boolean listPrivate){
        UserSettings settings = settingsPersistencePort.findByUserId(userId)
                .orElseThrow(()-> new IllegalArgumentException("Configuracion no encontrada"));

        settings.updateListVisibility(listPrivate);
        settingsPersistencePort.save(settings);
    }
}
