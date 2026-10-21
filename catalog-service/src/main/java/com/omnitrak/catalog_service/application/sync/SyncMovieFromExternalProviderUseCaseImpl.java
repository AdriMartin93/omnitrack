package com.omnitrak.catalog_service.application.sync;

import com.omnitrak.catalog_service.domain.annotations.UseCase;
import com.omnitrak.catalog_service.domain.models.media.MovieItem;
import com.omnitrak.catalog_service.domain.ports.in.sync.SyncMovieFromExternalProviderUseCase;
import com.omnitrak.catalog_service.domain.ports.out.external.ExternalMovieProviderPort;
import com.omnitrak.catalog_service.domain.ports.out.persistence.CatalogPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;


@UseCase
@RequiredArgsConstructor
public class SyncMovieFromExternalProviderUseCaseImpl implements SyncMovieFromExternalProviderUseCase {

    private final ExternalMovieProviderPort externalMovieProviderPort;
    private final CatalogPersistencePort catalogPersistencePort;

    @Override
    public MovieItem execute(Long tmdbMovieId){

        Optional<MovieItem> existingMovie = catalogPersistencePort
                .findByExternalProviderAndExternalId("TMDB", tmdbMovieId);

        if (existingMovie.isPresent()) {
            return existingMovie.get();
        }

        MovieItem movie = externalMovieProviderPort.fetchMovieById(tmdbMovieId)
                .orElseThrow(() -> new IllegalArgumentException("Película no encontrada en: "+ tmdbMovieId));

        return catalogPersistencePort.save(movie);
    }
}
