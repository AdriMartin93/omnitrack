package com.omnitrak.catalog_service.domain.ports.in.sync;

import com.omnitrak.catalog_service.domain.models.media.MovieItem;

public interface SyncMovieFromExternalProviderUseCase {
    MovieItem execute(Long tmdbMovieId);
}
