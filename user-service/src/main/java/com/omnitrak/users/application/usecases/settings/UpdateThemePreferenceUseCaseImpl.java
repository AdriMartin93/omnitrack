package com.omnitrak.users.application.usecases.settings;

import com.omnitrak.users.domain.annotations.UseCase;
import com.omnitrak.users.domain.models.UserSettings;
import com.omnitrak.users.domain.models.enums.Theme;
import com.omnitrak.users.domain.ports.in.settings.UpdateThemePreferenceUseCase;
import com.omnitrak.users.domain.ports.out.settings.SettingsPersistencePort;

import java.util.UUID;

@UseCase
public class UpdateThemePreferenceUseCaseImpl implements UpdateThemePreferenceUseCase {

    private final SettingsPersistencePort settingsPersistencePort;

    public UpdateThemePreferenceUseCaseImpl(SettingsPersistencePort settingsPersistencePort) {
        this.settingsPersistencePort = settingsPersistencePort;
    }


    @Override
    public void updateTheme(UUID userId, Theme theme) {
        UserSettings settings = settingsPersistencePort.findByUserId(userId)
                .orElseThrow(()-> new RuntimeException("Usuario no encontrado"));

        settings.changeTheme(theme);
        settingsPersistencePort.save(settings);
    }
}
