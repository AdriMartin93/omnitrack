package com.omnitrak.catalog_service.infrastructure.adapters.external.jikan;

import com.omnitrak.catalog_service.domain.models.media.MangaItem;
import com.omnitrak.catalog_service.domain.ports.out.external.ExternalMangaProviderPort;
import com.omnitrak.catalog_service.infrastructure.adapters.external.jikan.dto.JikanCommonDtos.ListResponse;
import com.omnitrak.catalog_service.infrastructure.adapters.external.jikan.dto.JikanCommonDtos.SingleResponse;
import com.omnitrak.catalog_service.infrastructure.adapters.external.jikan.dto.JikanMangaDto;
import com.omnitrak.catalog_service.infrastructure.adapters.external.jikan.mappers.JikanMangaMapper;
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
public class JikanMangaAdapter implements ExternalMangaProviderPort {

    private final RestClient jikanRestClient;
    private final JikanMangaMapper mangaMapper;

    @Override
    public Optional<MangaItem> fetchMangaById(Long malId) {
        if (malId == null) {
            return Optional.empty();
        }

        try {
            SingleResponse<JikanMangaDto> response = jikanRestClient.get()
                    .uri("/manga/{id}", malId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<SingleResponse<JikanMangaDto>>() {});

            if (response == null || response.getData() == null) {
                return Optional.empty();
            }

            return Optional.ofNullable(mangaMapper.toDomain(response.getData()));

        } catch (Exception e) {
            log.error("Error al obtener manga de Jikan con malId {}: {}", malId, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<MangaItem> searchMangaByTitle(String query) {
        if (query == null || query.isBlank()) {
            return Collections.emptyList();
        }

        try {
            ListResponse<JikanMangaDto> response = jikanRestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/manga")
                            .queryParam("q", query.trim())
                            .queryParam("limit", 20)
                            .build())
                    .retrieve()
                    .body(new ParameterizedTypeReference<ListResponse<JikanMangaDto>>() {});

            if (response == null || response.getData() == null) {
                return Collections.emptyList();
            }

            return response.getData().stream()
                    .map(mangaMapper::toDomain)
                    .filter(Objects::nonNull)
                    .toList();

        } catch (Exception e) {
            log.error("Error al buscar manga en Jikan con query '{}': {}", query, e.getMessage());
            return Collections.emptyList();
        }
    }
}