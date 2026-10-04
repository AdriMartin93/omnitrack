package com.omnitrak.catalog_service.domain.models.episodic;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class Season {

    private final int seasonNumber;
    private final String name;
    private final LocalDate airDate;
    private final List<Episode> episodes;
}
