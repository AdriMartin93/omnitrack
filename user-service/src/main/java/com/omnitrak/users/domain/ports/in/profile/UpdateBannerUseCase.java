package com.omnitrak.users.domain.ports.in.profile;

import java.util.UUID;

public interface UpdateBannerUseCase {
    void updateBanner(UUID userId, String newBanner);
}
