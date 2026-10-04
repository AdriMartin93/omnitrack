package com.omnitrak.catalog_service.domain.models.media;


import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class MovieItem extends MediaItem {
    private final Integer durationMinutes;
    private final String director;
}
