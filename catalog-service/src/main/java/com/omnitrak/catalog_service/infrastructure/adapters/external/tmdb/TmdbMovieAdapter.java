package com.omnitrak.catalog_service.infrastructure.adapters.external.tmdb;

import com.omnitrak.catalog_service.domain.models.media.MovieItem;
import com.omnitrak.catalog_service.domain.ports.out.external.ExternalMovieProviderPort;
import com.omnitrak.catalog_service.infrastructure.adapters.external.tmdb.dto.TmdbMovieDto;
import com.omnitrak.catalog_service.infrastructure.adapters.external.tmdb.dto.TmdbMovieSearchResponseDto;
import com.omnitrak.catalog_service.infrastructure.adapters.external.tmdb.mappers.TmdbMovieMapper;
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
public class TmdbMovieAdapter implements ExternalMovieProviderPort {

    private final RestClient tmdbRestClient;
    private final TmdbMovieMapper movieMapper;

    @Override
    public Optional<MovieItem> fetchMovieById(Long externalId){
        try {
            TmdbMovieDto response = tmdbRestClient.get()

                    .uri("/movie/{id}?language=es-ES", externalId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.warn("Película no encontrada en TMDB para externalId: {} (Status: {})",
                                externalId, res.getStatusCode());
                    })

                    .body(TmdbMovieDto.class);

            return Optional.ofNullable(movieMapper.toDomain(response));

        } catch (Exception ex) {
            log.error("Error al consultar película en TMDB con ID {}: {}", externalId, ex.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<MovieItem> searchMoviesByTitle(String query) {
        if (query == null || query.isBlank()) {
            return Collections.emptyList();
        }

        try {
            TmdbMovieSearchResponseDto response = tmdbRestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/search/movie")
                            .queryParam("query", query.trim())
                            .queryParam("language", "es-ES")
                            .build())
                    .retrieve()
                    .body(TmdbMovieSearchResponseDto.class);

            if (response == null || response.getResults() == null) {
                return Collections.emptyList();
            }

            return response.getResults().stream()
                    .map(movieMapper::toDomain)
                    .filter(Objects::nonNull)
                    .toList();

        } catch (Exception ex) {
            log.error("Error al buscar películas en TMDB con query '{}': {}", query, ex.getMessage());
            return Collections.emptyList();
        }
    }
}
