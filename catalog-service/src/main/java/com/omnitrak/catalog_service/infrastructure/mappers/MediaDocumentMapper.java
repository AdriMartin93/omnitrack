package com.omnitrak.catalog_service.infrastructure.mappers;


import com.omnitrak.catalog_service.domain.models.media.*;
import com.omnitrak.catalog_service.infrastructure.entities.MediaDocument;
import com.omnitrak.catalog_service.infrastructure.mappers.strategies.MediaItemMapperStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MediaDocumentMapper {

    @SuppressWarnings("rawtypes")
    private final List<MediaItemMapperStrategy> strategies;

    @SuppressWarnings("unchecked")
    public MediaDocument toDocument(MediaItem item) {
        if (item == null) return null;

        return strategies.stream()
                .filter(strategy -> strategy.supportsDomain(item))
                .findFirst()
                .map(strategy -> strategy.toDocument(item))
                .orElseThrow(() -> new IllegalArgumentException("No hay mapper registrado para: " + item.getClass().getName()));
    }

    @SuppressWarnings("unchecked")
    public <T extends MediaItem> T toDomain(MediaDocument document) {
        if (document == null) return null;

        return (T) strategies.stream()
                .filter(strategy -> strategy.supportsEntity(document))
                .findFirst()
                .map(strategy -> strategy.toDomain(document))
                .orElseThrow(() -> new IllegalArgumentException("No hay mapper registrado para: " + document.getClass().getName()));
    }
}
