package com.omnitrak.catalog_service.application.query;

import com.omnitrak.catalog_service.domain.annotations.UseCase;
import com.omnitrak.catalog_service.domain.models.enums.AnimeSeason;
import com.omnitrak.catalog_service.domain.models.media.AnimeItem;
import com.omnitrak.catalog_service.domain.ports.in.query.GetAnimeBySeasonUseCase;
import com.omnitrak.catalog_service.domain.ports.out.persistence.CatalogPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetAnimeBySeasonUseCaseImpl implements GetAnimeBySeasonUseCase {

    private final CatalogPersistencePort persistencePort;

    @Override
    public List<AnimeItem> execute(int year, AnimeSeason season){
        return persistencePort.findAnimeBySeason(year,season);
    }
}
