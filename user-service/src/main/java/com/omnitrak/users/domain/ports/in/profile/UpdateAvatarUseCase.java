package com.omnitrak.users.domain.ports.in.profile;

import java.util.UUID;

public interface UpdateAvatarUseCase {
    void updateAvatar(UUID userId, String newAvatar);
}
