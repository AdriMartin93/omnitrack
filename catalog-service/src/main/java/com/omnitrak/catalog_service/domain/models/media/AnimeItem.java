package com.omnitrak.catalog_service.domain.models.media;

import com.omnitrak.catalog_service.domain.models.enums.AnimeSeason;
import com.omnitrak.catalog_service.domain.models.episodic.Episode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class AnimeItem extends SeriesItem {
    private final List<String> studios;
    private final String source;
    private final Integer seasonYear;
    private final AnimeSeason season;
    private final Integer episodeDurationMinutes;
    private final String ageRating;
    private final String broadcastDay;
}
