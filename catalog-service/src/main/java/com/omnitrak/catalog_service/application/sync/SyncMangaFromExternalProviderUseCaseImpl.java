package com.omnitrak.catalog_service.application.sync;

import com.omnitrak.catalog_service.domain.annotations.UseCase;
import com.omnitrak.catalog_service.domain.models.media.MangaItem;
import com.omnitrak.catalog_service.domain.ports.in.sync.SyncMangaFromExternalProviderUseCase;
import com.omnitrak.catalog_service.domain.ports.out.external.ExternalMangaProviderPort;
import com.omnitrak.catalog_service.domain.ports.out.persistence.CatalogPersistencePort;
import lombok.RequiredArgsConstructor;


@UseCase
@RequiredArgsConstructor
public class SyncMangaFromExternalProviderUseCaseImpl implements SyncMangaFromExternalProviderUseCase {

    private final ExternalMangaProviderPort externalMangaProviderPort;
    private final CatalogPersistencePort catalogPersistencePort;

    @Override
    public MangaItem execute(Long externalMalId){
        MangaItem manga = externalMangaProviderPort.fetchMangaById(externalMalId)
                .orElseThrow(() -> new IllegalArgumentException("Manga no encontrado con id :" + externalMalId));

        return catalogPersistencePort.save(manga);
    }
}
