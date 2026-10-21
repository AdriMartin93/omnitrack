package com.omnitrak.catalog_service.application.query;

import com.omnitrak.catalog_service.domain.annotations.UseCase;
import com.omnitrak.catalog_service.domain.models.enums.MediaType;
import com.omnitrak.catalog_service.domain.models.media.MediaItem;
import com.omnitrak.catalog_service.domain.ports.in.query.SearchExternalMediaUseCase;
import com.omnitrak.catalog_service.domain.ports.out.external.*;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class SearchExternalMediaUseCaseImpl implements SearchExternalMediaUseCase {

    private final ExternalMovieProviderPort movieProviderPort;
    private final ExternalSeriesProviderPort seriesProviderPort;
    private final ExternalGameProviderPort gameProviderPort;
    private final ExternalAnimeProviderPort animeProviderPort;
    private final ExternalMangaProviderPort mangaProviderPort;

    @Override
    public List<? extends MediaItem> execute(String query, MediaType type) {
        if (query == null || query.isBlank() || type == null) {
            return Collections.emptyList();
        }

        String sanitizedQuery = query.trim();

        return switch (type) {
            case MOVIE -> movieProviderPort.searchMoviesByTitle(sanitizedQuery);
            case SERIES -> seriesProviderPort.searchSeriesByTitle(sanitizedQuery);
            case VIDEOGAME -> gameProviderPort.searchGamesByTitle(sanitizedQuery);
            case ANIME -> animeProviderPort.searchAnimeByTitle(sanitizedQuery);
            case MANGA -> mangaProviderPort.searchMangaByTitle(sanitizedQuery);
        };
    }
}
