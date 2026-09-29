package com.omnitrak.users.domain.ports.in.profile;

import java.util.UUID;

public interface UpdateUsernameUseCase {
    void updateUsername(UUID userId, String newUsername);
}
