package com.omnitrak.catalog_service.domain.ports.in.sync;

import com.omnitrak.catalog_service.domain.models.media.MangaItem;

public interface SyncMangaFromExternalProviderUseCase {
    MangaItem execute(Long externalMalId);
}
