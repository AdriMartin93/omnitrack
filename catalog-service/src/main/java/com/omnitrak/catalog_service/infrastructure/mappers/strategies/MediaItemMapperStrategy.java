package com.omnitrak.catalog_service.infrastructure.mappers.strategies;

import com.omnitrak.catalog_service.domain.models.media.MediaItem;
import com.omnitrak.catalog_service.infrastructure.entities.MediaDocument;

public interface MediaItemMapperStrategy <D extends MediaItem, E extends MediaDocument>{

    boolean supportsDomain(MediaItem item);

    boolean supportsEntity(MediaDocument document);

    E toDocument(D domain);
    D toDomain(E document);
}
