package com.omnitrak.users.domain.ports.in.profile;

import com.omnitrak.users.domain.models.PublicProfile;
import com.omnitrak.users.domain.models.User;

import java.util.UUID;

public interface GetPublicProfileUseCase {
    PublicProfile getById(UUID userId);
    PublicProfile getByUsername(String username);
}
