package com.omnitrak.catalog_service.application.query;


import com.omnitrak.catalog_service.domain.annotations.UseCase;
import com.omnitrak.catalog_service.domain.models.media.MediaItem;
import com.omnitrak.catalog_service.domain.ports.in.query.GetMediaDetailsUseCase;
import com.omnitrak.catalog_service.domain.ports.out.persistence.CatalogPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class GetMediaDetailsUseCaseImpl implements GetMediaDetailsUseCase {

    private final CatalogPersistencePort persistencePort;

    @Override
    public Optional<MediaItem> execute(String id) {
        return persistencePort.findById(id);
    }
}
