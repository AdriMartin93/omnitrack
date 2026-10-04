package com.omnitrak.catalog_service.domain.ports.in.sync;

import com.omnitrak.catalog_service.domain.models.media.SeriesItem;

public interface SyncSeriesFromExternalProviderUseCase {
    SeriesItem execute(Long tmdbSeriesId);
}
