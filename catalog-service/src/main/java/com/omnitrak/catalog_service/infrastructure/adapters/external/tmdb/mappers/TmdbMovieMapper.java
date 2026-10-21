package com.omnitrak.catalog_service.infrastructure.adapters.external.tmdb.mappers;


import com.omnitrak.catalog_service.domain.models.enums.MediaType;
import com.omnitrak.catalog_service.domain.models.enums.ReleaseStatus;
import com.omnitrak.catalog_service.domain.models.enums.SyncStatus;
import com.omnitrak.catalog_service.domain.models.media.MovieItem;
import com.omnitrak.catalog_service.infrastructure.adapters.external.tmdb.dto.TmdbMovieDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

@Component
public class TmdbMovieMapper {

    @Value("${external.tmdb.image-base-url:https://image.tmdb.org/t/p/w500}")
    private String imageBaseUrl;

    public MovieItem toDomain(TmdbMovieDto dto) {
        if (dto == null) return null;

        Instant releaseDateInstant = null;
        if (dto.getReleaseDate() != null && !dto.getReleaseDate().isBlank()) {
            releaseDateInstant = LocalDate.parse(dto.getReleaseDate())
                    .atStartOfDay()
                    .toInstant(ZoneOffset.UTC);
        }

        List<String> genres = (dto.getGenres() == null)
                ? Collections.emptyList()
                : dto.getGenres().stream().map(TmdbMovieDto.TmdbGenreDto::getName).toList();

        List<String> companies = (dto.getProductionCompanies() == null)
                ? Collections.emptyList()
                : dto.getProductionCompanies().stream().map(TmdbMovieDto.TmdbProductionCompanyDto::getName).toList();

        return MovieItem.builder()
                .title(dto.getTitle())
                .originalTitle(dto.getOriginalTitle())
                .alternativeTitles(Collections.emptyList())
                .synopsis(dto.getOverview())

                .posterUrl(dto.getPosterPath() != null ? imageBaseUrl + dto.getPosterPath() : null)
                .bannerUrl(dto.getBackdropPath() != null ? imageBaseUrl + dto.getBackdropPath() : null)
                .releaseDate(releaseDateInstant)
                .genres(genres)
                .type(MediaType.MOVIE)
                .status(mapStatus(dto.getStatus()))
                .externalProvider("TMDB")
                .externalId(dto.getId())
                .lastSyncedAt(Instant.now())
                .syncStatus(SyncStatus.SYNCED)
                .durationMinutes(dto.getRuntime())
                .productionCompanies(companies)
                .directors(Collections.emptyList())
                .build();
    }

    private ReleaseStatus mapStatus(String tmdbStatus) {
        if (tmdbStatus == null) return ReleaseStatus.UPCOMING;
        return switch (tmdbStatus.toLowerCase()) {
            case "released" -> ReleaseStatus.RELEASED;
            case "in production", "post production", "planned" -> ReleaseStatus.UPCOMING;
            case "canceled" -> ReleaseStatus.CANCELLED;
            default -> ReleaseStatus.UPCOMING;
        };
    }
}
