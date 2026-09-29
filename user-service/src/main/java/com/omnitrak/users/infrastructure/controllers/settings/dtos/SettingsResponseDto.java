package com.omnitrak.users.infrastructure.controllers.settings.dtos;

import com.omnitrak.users.domain.models.UserSettings;
import com.omnitrak.users.domain.models.enums.Theme;

import java.util.UUID;

public record SettingsResponseDto(
        UUID userId,
        Theme theme,
        boolean profileVisible,
        boolean listPrivate) {

    public static SettingsResponseDto fromDomain(UserSettings settings){
        return new SettingsResponseDto(
                settings.getUserId(),
                settings.getTheme(),
                settings.isProfilePrivate(),
                settings.isListPrivate()
        );
    }
}
