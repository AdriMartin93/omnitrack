package com.omnitrak.catalog_service.infrastructure.adapters.external.jikan.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import com.omnitrak.catalog_service.infrastructure.adapters.external.jikan.dto.JikanCommonDtos.*;

import java.util.List;

@Data
public class JikanAnimeDto {

    @JsonProperty("mal_id")
    private Long malId;

    private String title;

    @JsonProperty("title_japanese")
    private String titleJapanese;

    private String synopsis;
    private JikanImagesDto images;
    private JikanDatesDto aired;
    private String status;
    private Integer episodes;
    private String duration; // duracion del episodio
    private String rating; // la edad, tipo pg-18, pg-13 ...
    private String season;
    private Integer year;
    private JikanBroadcastDto broadcast;
    private String source; // de dodne viene: manga, LN, original...
    private List<JikanNamedDto> studios;
    private List<JikanNamedDto> genres;

}
