package com.omnitrak.catalog_service.application.sync;

import com.omnitrak.catalog_service.domain.annotations.UseCase;
import com.omnitrak.catalog_service.domain.models.media.VideoGameItem;
import com.omnitrak.catalog_service.domain.ports.in.sync.SyncGameFromExternalProviderUseCase;
import com.omnitrak.catalog_service.domain.ports.out.external.ExternalGameProviderPort;
import com.omnitrak.catalog_service.domain.ports.out.persistence.CatalogPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;


@UseCase
@RequiredArgsConstructor
public class SyncGameFromExternalProviderUseCaseImpl implements SyncGameFromExternalProviderUseCase {

    private final ExternalGameProviderPort externalGameProviderPort;
    private final CatalogPersistencePort catalogPersistencePort;

    @Override
    public VideoGameItem execute(Long igdbGameId){

        Optional<VideoGameItem> existingGame = catalogPersistencePort
                .findByExternalProviderAndExternalId("IGDB", igdbGameId);

        if (existingGame.isPresent()) {
            return existingGame.get();
        }

        VideoGameItem game = externalGameProviderPort.fetchGameById(igdbGameId)
                .orElseThrow(()-> new IllegalArgumentException("El juego no se ha encontrado :" + igdbGameId));

        return catalogPersistencePort.save(game);
    }
}
