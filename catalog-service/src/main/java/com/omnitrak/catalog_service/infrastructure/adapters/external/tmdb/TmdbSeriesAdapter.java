package com.omnitrak.catalog_service.infrastructure.adapters.external.tmdb;

import com.omnitrak.catalog_service.domain.models.media.SeriesItem;
import com.omnitrak.catalog_service.domain.ports.out.external.ExternalSeriesProviderPort;
import com.omnitrak.catalog_service.infrastructure.adapters.external.tmdb.dto.TmdbSeriesDto;
import com.omnitrak.catalog_service.infrastructure.adapters.external.tmdb.dto.TmdbSeriesSearchResponseDto;
import com.omnitrak.catalog_service.infrastructure.adapters.external.tmdb.mappers.TmdbSeriesMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TmdbSeriesAdapter implements ExternalSeriesProviderPort {

    private final RestClient tmdbRestClient;
    private final TmdbSeriesMapper seriesMapper;

    @Override
    public Optional<SeriesItem> fetchSeriesById(Long tmdbSeriesId) {
        try {
            TmdbSeriesDto response = tmdbRestClient.get()

                    .uri("/tv/{id}?language=es-ES", tmdbSeriesId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.warn("Serie no encontrada en TMDB para tmdbSeriesId: {} (Status: {})",
                                tmdbSeriesId, res.getStatusCode());
                    })
                    .body(TmdbSeriesDto.class);

            return Optional.ofNullable(seriesMapper.toDomain(response));

        } catch (Exception ex) {
            log.error("Error al consultar serie en TMDB con ID {}: {}", tmdbSeriesId, ex.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<SeriesItem> searchSeriesByTitle(String query) {
        if (query == null || query.isBlank()) {
            return Collections.emptyList();
        }

        try {
            TmdbSeriesSearchResponseDto response = tmdbRestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/search/tv")
                            .queryParam("query", query.trim())   // Escapa caracteres especiales
                            .queryParam("language", "es-ES")
                            .build())
                    .retrieve()
                    .body(TmdbSeriesSearchResponseDto.class);

            if (response == null || response.getResults() == null) {
                return Collections.emptyList();
            }

            return response.getResults().stream()
                    .map(seriesMapper::toDomain)
                    .filter(Objects::nonNull)
                    .toList();

        } catch (Exception ex) {
            log.error("Error al buscar series en TMDB con query '{}': {}", query, ex.getMessage());
            return Collections.emptyList();
        }
    }
}