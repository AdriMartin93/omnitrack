package com.omnitrak.catalog_service.infrastructure.adapters.external.igdb;

import com.omnitrak.catalog_service.domain.models.media.VideoGameItem;
import com.omnitrak.catalog_service.domain.ports.out.external.ExternalGameProviderPort;
import com.omnitrak.catalog_service.infrastructure.adapters.external.igdb.dto.IgdbGameDto;
import com.omnitrak.catalog_service.infrastructure.adapters.external.igdb.mappers.IgdbGameMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class IgdbGameAdapter implements ExternalGameProviderPort {

    private final RestClient igdbRestClient;
    private final IgdbGameMapper gameMapper;

    private static final String GAME_FIELDS = "fields name, summary, first_release_date, status, " +
            "cover.url, genres.name, platforms.name, game_modes.name, game_engines.name, " +
            "involved_companies.developer, involved_companies.publisher, involved_companies.company.name; ";

    @Override
    public Optional<VideoGameItem> fetchGameById(Long igdbId) {
        if (igdbId == null) return Optional.empty();

        String apicalypseBody = GAME_FIELDS + "where id = " + igdbId + ";";

        try {
            List<IgdbGameDto> results = igdbRestClient.post()
                    .uri("/games")
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(apicalypseBody)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<IgdbGameDto>>() {});

            if (results == null || results.isEmpty()) {
                return Optional.empty();
            }

            return Optional.ofNullable(gameMapper.toDomain(results.get(0)));

        } catch (Exception e) {
            log.error("Error al consultar videojuego en IGDB con ID {}: {}", igdbId, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<VideoGameItem> searchGamesByTitle(String query) {
        if (query == null || query.isBlank()) {
            return Collections.emptyList();
        }

        String sanitizedQuery = query.trim().replace("\"", "\\\"");

        String apicalypseBody = GAME_FIELDS + "search \"" + sanitizedQuery + "\"; limit 20;";

        try {
            List<IgdbGameDto> results = igdbRestClient.post()
                    .uri("/games")
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(apicalypseBody)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<IgdbGameDto>>() {});

            if (results == null || results.isEmpty()) {
                return Collections.emptyList();
            }
            return results.stream()
                    .map(gameMapper::toDomain)
                    .filter(Objects::nonNull)
                    .toList();

        } catch (Exception e) {
            log.error("Error al buscar videojuegos en IGDB para la query '{}': {}", query, e.getMessage());
            return Collections.emptyList();
        }
    }
}