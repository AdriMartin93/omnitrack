package com.omnitrak.catalog_service.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeDocument {
    private Integer episodeNumber;
    private String title;
    private String synopsis;
    private Instant airDate;
    private Integer durationMinutes;
    private String thumbnailImageUrl;
}
