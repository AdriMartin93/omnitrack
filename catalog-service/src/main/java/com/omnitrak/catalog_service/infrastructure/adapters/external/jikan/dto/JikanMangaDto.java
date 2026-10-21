package com.omnitrak.catalog_service.infrastructure.adapters.external.jikan.dto;

import com.omnitrak.catalog_service.infrastructure.adapters.external.jikan.dto.JikanCommonDtos.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class JikanMangaDto {

    @JsonProperty("mal_id")
    private Long malId;

    private String title;

    @JsonProperty("title_japanese")
    private String titleJapanese;

    private String synopsis;
    private JikanImagesDto images;
    private JikanDatesDto published;
    private String status;
    private Integer volumes;
    private Integer chapters;
    private List<JikanNamedDto> authors;
    private List<JikanNamedDto> serializations;
    private List<JikanNamedDto> demographics;
    private List<JikanNamedDto> genres;
}
