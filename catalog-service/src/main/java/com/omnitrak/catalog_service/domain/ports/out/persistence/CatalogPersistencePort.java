package com.omnitrak.catalog_service.domain.ports.out.persistence;

import com.omnitrak.catalog_service.domain.models.media.AnimeItem;
import com.omnitrak.catalog_service.domain.models.values.CatalogFilter;
import com.omnitrak.catalog_service.domain.models.media.MediaItem;
import com.omnitrak.catalog_service.domain.models.enums.AnimeSeason;
import com.omnitrak.catalog_service.domain.models.enums.MediaType;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface CatalogPersistencePort {

    <T extends MediaItem> T save(T mediaItem);
    Optional<MediaItem> findById(String id);
    <T extends MediaItem> Optional<T> findByExternalProviderAndExternalId(String externalProvider, Long externalId);
    List<MediaItem> findUpcoming(Instant from, Instant to);
    List<MediaItem> search(String query, MediaType type, int page, int size);
    boolean existsById(String id);
    List<MediaItem> findByTypeAndReleaseDateBetween(MediaType type, Instant from, Instant to);
    List<MediaItem> findByCriteria(CatalogFilter filter);
}
