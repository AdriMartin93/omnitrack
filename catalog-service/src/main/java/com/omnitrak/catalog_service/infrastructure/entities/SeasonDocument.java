package com.omnitrak.catalog_service.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeasonDocument {
    private Integer seasonNumber;
    private String name;
    private String overview;
    private String posterUrl;
    private Integer totalEpisodes;
    private LocalDate airDate;
    private List<EpisodeDocument> episodes;
}
