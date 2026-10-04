package com.omnitrak.catalog_service.domain.ports.out.external;

import com.omnitrak.catalog_service.domain.models.media.SeriesItem;

import java.util.List;
import java.util.Optional;

public interface ExternalSeriesProviderPort {

    Optional<SeriesItem> fetchSeriesById(Long tmdbSeriesId);
    List<SeriesItem> searchSeriesByTitle(String query);
}
