package com.omnitrak.catalog_service.infrastructure.entities;


import com.omnitrak.catalog_service.domain.models.enums.AnimeSeason;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.CompoundIndex;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@CompoundIndex(name = "idx_anime_season_year", def = "{'seasonYear': 1, 'season': 1}") // Índice de MongoDB para filtrar rapido por año y temporada
public class AnimeDocument extends SeriesDocument{

    public static final String FIELD_SEASON_YEAR = "seasonYear";
    public static final String FIELD_SEASON = "season";

    private List<String> studios;
    private String source;
    private Integer seasonYear;
    private AnimeSeason season;
    private Integer episodeDurationMinutes;
    private String ageRating;
    private String broadcastDay;
}
