package com.omnitrak.users.domain.ports.in.profile;

import java.util.UUID;

public interface UpdateBioUseCase {
    void updateBio(UUID userId, String newBio);
}
