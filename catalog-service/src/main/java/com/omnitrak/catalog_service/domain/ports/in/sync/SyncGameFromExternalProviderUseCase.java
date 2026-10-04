package com.omnitrak.catalog_service.domain.ports.in.sync;

import com.omnitrak.catalog_service.domain.models.media.VideoGameItem;

public interface SyncGameFromExternalProviderUseCase {
    VideoGameItem execute(Long igdbGameId);
}
