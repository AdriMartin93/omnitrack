package com.omnitrak.catalog_service.infrastructure.controllers.dto;


import com.omnitrak.catalog_service.domain.models.enums.AnimeSeason;
import com.omnitrak.catalog_service.domain.models.enums.MediaType;
import com.omnitrak.catalog_service.domain.models.enums.ReleaseStatus;
import com.omnitrak.catalog_service.domain.models.values.CatalogFilter;
import lombok.Data;

import java.util.List;

@Data
public class CatalogFilterRequest {

    private MediaType mediaType;
    private String genre;
    private ReleaseStatus status;
    private Integer releaseYear;
    private String platform;
    private List<String> authors;
    private Integer seasonYear;
    private AnimeSeason season;
    private int page = 0;
    private int size = 20;

    public CatalogFilter toDomain() {
        return CatalogFilter.builder()
                .mediaType(this.mediaType)
                .genre(this.genre)
                .status(this.status)
                .releaseYear(this.releaseYear)
                .platform(this.platform)
                .authors(this.authors)
                .seasonYear(this.seasonYear)
                .season(this.season)
                .page(this.page)
                .size(this.size)
                .build();
    }
}
