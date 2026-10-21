package com.omnitrak.catalog_service.application.sync;

import com.omnitrak.catalog_service.domain.annotations.UseCase;
import com.omnitrak.catalog_service.domain.models.media.AnimeItem;
import com.omnitrak.catalog_service.domain.ports.in.sync.SyncAnimeFromExternalProviderUseCase;
import com.omnitrak.catalog_service.domain.ports.out.external.ExternalAnimeProviderPort;
import com.omnitrak.catalog_service.domain.ports.out.persistence.CatalogPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class SyncAnimeFromExternalProviderUseCaseImpl implements SyncAnimeFromExternalProviderUseCase {

    private final ExternalAnimeProviderPort externalAnimeProviderPort;
    private final CatalogPersistencePort catalogPersistencePort;

    @Override
    public AnimeItem execute(Long externalId){

        Optional<AnimeItem> existingAnime = catalogPersistencePort
                .findByExternalProviderAndExternalId("MAL", externalId);

        if(existingAnime.isPresent()){
            return existingAnime.get();
        }

        AnimeItem anime = externalAnimeProviderPort.fetchAnimeById(externalId)
                .orElseThrow(()-> new IllegalArgumentException("Anime no encontrado en: "+externalId));

        return catalogPersistencePort.save(anime);
    }
}
