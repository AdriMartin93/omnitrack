package com.omnitrak.catalog_service.domain.models.values;


import com.omnitrak.catalog_service.domain.models.enums.MediaType;
import com.omnitrak.catalog_service.domain.models.enums.ReleaseStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CatalogFilter {

    private final MediaType mediaType;
    private final String genre;
    private final Integer releaseYear;
    private final ReleaseStatus status;
    private final String platform;  // este es solo para juegos
    private final List<String> authors; // con autor me refiero a manga
    private final int page;
    private final int size;

}
