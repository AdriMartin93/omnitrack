package com.omnitrak.catalog_service.application.sync;

import com.omnitrak.catalog_service.domain.annotations.UseCase;
import com.omnitrak.catalog_service.domain.models.media.SeriesItem;
import com.omnitrak.catalog_service.domain.ports.in.sync.SyncSeriesFromExternalProviderUseCase;
import com.omnitrak.catalog_service.domain.ports.out.external.ExternalSeriesProviderPort;
import com.omnitrak.catalog_service.domain.ports.out.persistence.CatalogPersistencePort;
import lombok.RequiredArgsConstructor;


@UseCase
@RequiredArgsConstructor
public class SyncSeriesFromExternalProviderUseCaseImpl implements SyncSeriesFromExternalProviderUseCase {

    private final ExternalSeriesProviderPort externalSeriesProviderPort;
    private final CatalogPersistencePort catalogPersistencePort;

    @Override
    public SeriesItem execute(Long tmdbSeriesId){
        SeriesItem series = externalSeriesProviderPort.fetchSeriesById(tmdbSeriesId)
                .orElseThrow(()-> new IllegalArgumentException("Serie no encontrada en: "+ tmdbSeriesId));

        return catalogPersistencePort.save(series);
    }
}
