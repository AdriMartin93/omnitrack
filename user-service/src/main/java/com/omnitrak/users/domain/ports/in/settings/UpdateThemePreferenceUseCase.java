package com.omnitrak.users.domain.ports.in.settings;

import com.omnitrak.users.domain.models.enums.Theme;

import java.util.UUID;

public interface UpdateThemePreferenceUseCase {
    void updateTheme(UUID userId, Theme theme);
}
