package com.omnitrak.catalog_service.infrastructure.adapters.external.jikan;

import com.omnitrak.catalog_service.domain.models.enums.AnimeSeason;
import com.omnitrak.catalog_service.domain.models.media.AnimeItem;
import com.omnitrak.catalog_service.domain.ports.out.external.ExternalAnimeProviderPort;
import com.omnitrak.catalog_service.infrastructure.adapters.external.jikan.dto.JikanAnimeDto;
import com.omnitrak.catalog_service.infrastructure.adapters.external.jikan.dto.JikanCommonDtos.ListResponse;
import com.omnitrak.catalog_service.infrastructure.adapters.external.jikan.dto.JikanCommonDtos.SingleResponse;
import com.omnitrak.catalog_service.infrastructure.adapters.external.jikan.mappers.JikanAnimeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JikanAnimeAdapter implements ExternalAnimeProviderPort {

    private final RestClient jikanRestClient;
    private final JikanAnimeMapper animeMapper;

    @Override
    public Optional<AnimeItem> fetchAnimeById(Long malId) {
        if (malId == null) {
            return Optional.empty();
        }

        try {
            SingleResponse<JikanAnimeDto> response = jikanRestClient.get()
                    .uri("/anime/{id}", malId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<SingleResponse<JikanAnimeDto>>() {});

            if (response == null || response.getData() == null) {
                return Optional.empty();
            }

            return Optional.ofNullable(animeMapper.toDomain(response.getData()));

        } catch (Exception e) {
            log.error("Error al obtener anime de Jikan con malId {}: {}", malId, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<AnimeItem> searchAnimeByTitle(String query) {
        if (query == null || query.isBlank()) {
            return Collections.emptyList();
        }

        try {
            ListResponse<JikanAnimeDto> response = jikanRestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/anime")
                            .queryParam("q", query.trim())
                            .queryParam("limit", 20)
                            .build())
                    .retrieve()
                    .body(new ParameterizedTypeReference<ListResponse<JikanAnimeDto>>() {});

            return extractDomainList(response);

        } catch (Exception e) {
            log.error("Error al buscar anime en Jikan con query '{}': {}", query, e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<AnimeItem> fetchCurrentSeasonAnime() {
        try {
            ListResponse<JikanAnimeDto> response = jikanRestClient.get()
                    .uri("/seasons/now?limit=25")
                    .retrieve()
                    .body(new ParameterizedTypeReference<ListResponse<JikanAnimeDto>>() {});

            return extractDomainList(response);

        } catch (Exception e) {
            log.error("Error al obtener animes de la temporada actual en Jikan: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<AnimeItem> fetchAnimeBySeason(int year, AnimeSeason season) {
        if (season == null) {
            return Collections.emptyList();
        }

        try {
            String seasonValue = season.name().toLowerCase();

            ListResponse<JikanAnimeDto> response = jikanRestClient.get()
                    .uri("/seasons/{year}/{season}?limit=25", year, seasonValue)
                    .retrieve()
                    .body(new ParameterizedTypeReference<ListResponse<JikanAnimeDto>>() {});

            return extractDomainList(response);

        } catch (Exception e) {
            log.error("Error al obtener animes en Jikan para la temporada {}/{}: {}", season, year, e.getMessage());
            return Collections.emptyList();
        }
    }

    private List<AnimeItem> extractDomainList(ListResponse<JikanAnimeDto> response) {
        if (response == null || response.getData() == null) {
            return Collections.emptyList();
        }

        return response.getData().stream()
                .map(animeMapper::toDomain)
                .filter(Objects::nonNull)
                .toList();
    }
}
