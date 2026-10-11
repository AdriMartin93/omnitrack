package com.omnitrak.catalog_service.domain.models.media;

import com.omnitrak.catalog_service.domain.models.enums.AnimeSeason;
import com.omnitrak.catalog_service.domain.models.episodic.Episode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class AnimeItem extends SeriesItem {
    private final String animationStudio;
    private final String source;
    private final Integer seasonYear;
    private final AnimeSeason season;
    private final Episode episodes;
    private final Long externalMalId;
}
