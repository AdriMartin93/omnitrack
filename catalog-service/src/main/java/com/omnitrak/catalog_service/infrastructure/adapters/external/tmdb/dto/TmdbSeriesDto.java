package com.omnitrak.catalog_service.infrastructure.adapters.external.tmdb.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class TmdbSeriesDto {

   private Long id;
   private String name;

   @JsonProperty("original_name")
   private String originalName;

   private String overview;

   @JsonProperty("poster_path")
   private String posterPath;

   @JsonProperty("backdrop_path")
    private String backdropPath;

   @JsonProperty("first_air_date")
    private String firstAirDate;

   private String status;

   @JsonProperty("in_production")
    private Boolean inProduction;

   @JsonProperty("number_of_seasons")
    private Integer numberOfSeasons;

   @JsonProperty("number_of_episodes")
    private Integer numberOfEpisodes;

   private List<TmdbMovieDto.TmdbGenreDto> genres;

   private List<TmdbNetworkDto> networks;

   private List<TmdbSeasonSummaryDto> seasons;

   @Data
    public static class TmdbNetworkDto {
       private Integer id;
       private String name;
   }

    @Data
    public static class TmdbSeasonSummaryDto {
        private Long id;
        private String name;
        private String overview;

        @JsonProperty("season_number")
        private Integer seasonNumber;

        @JsonProperty("episode_count")
        private Integer episodeCount;

        @JsonProperty("air_date")
        private String airDate;

        @JsonProperty("poster_path")
        private String posterPath;
    }

}
