package com.omnitrak.catalog_service.domain.models.episodic;


import lombok.Builder;
import lombok.Getter;
import java.time.Instant;


@Getter
@Builder
public class Episode {
    private final String id;
    private final int episodeNumber;
    private final String title;
    private final String synopsis;
    private final Instant airDate;
    private final Integer durationMinutes;
}
