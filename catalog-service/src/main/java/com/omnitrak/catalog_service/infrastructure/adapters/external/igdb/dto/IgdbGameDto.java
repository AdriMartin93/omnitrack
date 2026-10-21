package com.omnitrak.catalog_service.infrastructure.adapters.external.igdb.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class IgdbGameDto {

    private Long id;

    private String name;

    private String summary;

    @JsonProperty("first_release_date")
    private Long firstReleaseDate;

    private IgdbCoverDto cover;

    private List<IgdbNamedItemDto> genres;

    private List<IgdbNamedItemDto> platforms;

    @JsonProperty("involved_companies")
    private List<IgdbInvolvedCompanyDto> involvedCompanies;

    @JsonProperty("game_modes")
    private List<IgdbNamedItemDto> gameModes;

    @JsonProperty("game_engines")
    private List<IgdbNamedItemDto> gameEngines;

    private Integer status;

    @Data
    public static class IgdbCoverDto {
        private Long id;
        private String url;
    }

    @Data
    public static class IgdbNamedItemDto {
        private Long id;
        private String name;
    }

    @Data
    public static class IgdbInvolvedCompanyDto {
        private Long id;
        private Boolean developer;
        private Boolean publisher;
        private IgdbNamedItemDto company;
    }
}
