package com.omnitrak.catalog_service.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeasonDocument {
    private Integer seasonNumber;
    private String title;
    private String synopsis;
    private Instant premiereDate;
    private List<EpisodeDocument> episodes;
}
