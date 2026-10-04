package com.omnitrak.catalog_service.domain.ports.out.external;

import com.omnitrak.catalog_service.domain.models.media.VideoGameItem;

import java.util.List;
import java.util.Optional;

public interface ExternalGameProviderPort {

    Optional<VideoGameItem> fetchGameById(Long igdbId);
    List<VideoGameItem> searchGamesByTitle(String query);
}
