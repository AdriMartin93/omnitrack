package com.omnitrak.catalog_service.domain.ports.out.external;

import com.omnitrak.catalog_service.domain.models.media.AnimeItem;
import com.omnitrak.catalog_service.domain.models.enums.AnimeSeason;

import java.util.List;
import java.util.Optional;

public interface ExternalAnimeProviderPort {

    Optional<AnimeItem> fetchAnimeById(Long malId);
    List<AnimeItem> searchAnimeByTitle(String query);
    List<AnimeItem> fetchCurrentSeasonAnime();
    List<AnimeItem> fetchAnimeBySeason(int year, AnimeSeason season);
}
