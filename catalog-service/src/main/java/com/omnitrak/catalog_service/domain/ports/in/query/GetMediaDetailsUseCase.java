package com.omnitrak.catalog_service.domain.ports.in.query;

import com.omnitrak.catalog_service.domain.models.media.MediaItem;

import java.util.Optional;

public interface GetMediaDetailsUseCase {
    Optional<MediaItem> execute(String id);
}
