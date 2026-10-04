package com.omnitrak.catalog_service.domain.ports.in.sync;

import com.omnitrak.catalog_service.domain.models.media.AnimeItem;

public interface SyncAnimeFromExternalProviderUseCase {

    AnimeItem execute(Long externalMalId);
}
