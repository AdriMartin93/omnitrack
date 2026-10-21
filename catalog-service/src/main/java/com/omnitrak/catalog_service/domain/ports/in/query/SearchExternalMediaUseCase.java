package com.omnitrak.catalog_service.domain.ports.in.query;

import com.omnitrak.catalog_service.domain.models.enums.MediaType;
import com.omnitrak.catalog_service.domain.models.media.MediaItem;

import java.util.List;

public interface SearchExternalMediaUseCase {
    List<? extends MediaItem> execute(String query, MediaType type);
}
