package com.omnitrak.catalog_service.domain.models.media;


import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class MovieItem extends MediaItem {
    private final Integer durationMinutes;
    private final List<String> directors;
    private final List<String> productionCompanies;
}
