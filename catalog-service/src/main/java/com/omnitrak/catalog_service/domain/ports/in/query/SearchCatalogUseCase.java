package com.omnitrak.catalog_service.domain.ports.in.query;

import com.omnitrak.catalog_service.domain.models.media.MediaItem;
import com.omnitrak.catalog_service.domain.models.enums.MediaType;

import java.util.List;

public interface SearchCatalogUseCase {
    List<MediaItem> execute(String query, MediaType type, int page, int size);
}
