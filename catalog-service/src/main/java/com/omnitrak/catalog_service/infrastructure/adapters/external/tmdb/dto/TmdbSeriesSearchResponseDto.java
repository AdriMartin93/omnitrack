package com.omnitrak.catalog_service.infrastructure.adapters.external.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class TmdbSeriesSearchResponseDto {

    private Integer page;

    @JsonProperty("total_results")
    private Integer totalResults;

    @JsonProperty("total_pages")
    private Integer totalPages;

    private List<TmdbSeriesDto> results = Collections.emptyList();
}
