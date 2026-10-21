package com.omnitrak.catalog_service.infrastructure.adapters.external.tmdb.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TmdbMovieDto {

    private Long id;
    private String title;

    @JsonProperty("original_title")
    private String originalTitle;

    private String overview;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("release_date")
    private String releaseDate; // Formato String: "YYYY-MM-DD"

    private Integer runtime;    // En minutos

    private String status;

    private List<TmdbGenreDto> genres;

    @JsonProperty("production_companies")
    private List<TmdbProductionCompanyDto> productionCompanies;

    @Data
    public static class TmdbGenreDto {
        private Integer id;
        private String name;
    }

    @Data
    public static class TmdbProductionCompanyDto {
        private Integer id;
        private String name;
    }
}
