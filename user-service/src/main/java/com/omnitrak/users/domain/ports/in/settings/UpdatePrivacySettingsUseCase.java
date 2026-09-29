package com.omnitrak.users.domain.ports.in.settings;

import java.util.UUID;

public interface UpdatePrivacySettingsUseCase {
    void updateProfileVisibility(UUID userId, boolean isPrivate);
    void updateListVisibility(UUID userId, boolean isPrivateList);
}
