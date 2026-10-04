package com.omnitrak.catalog_service.domain.ports.in.query;

import com.omnitrak.catalog_service.domain.models.media.AnimeItem;
import com.omnitrak.catalog_service.domain.models.enums.AnimeSeason;

import java.util.List;

public interface GetAnimeBySeasonUseCase {

    List<AnimeItem> execute(int year, AnimeSeason season);
}