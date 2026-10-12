package com.omnitrak.catalog_service.infrastructure.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SeriesDocument  extends MediaDocument{

    private List<SeasonDocument> seasons;
    private Integer totalSeasons;
    private Integer totalEpisodes;
    private List<String> networks; // hbo, netflix...
    private Boolean inProduction;
}
