package com.omnitrak.catalog_service.infrastructure.adapters.external.tmdb.mappers;

import com.omnitrak.catalog_service.domain.models.enums.MediaType;
import com.omnitrak.catalog_service.domain.models.enums.ReleaseStatus;
import com.omnitrak.catalog_service.domain.models.enums.SyncStatus;
import com.omnitrak.catalog_service.domain.models.episodic.Season;
import com.omnitrak.catalog_service.domain.models.media.SeriesItem;
import com.omnitrak.catalog_service.infrastructure.adapters.external.tmdb.dto.TmdbMovieDto;
import com.omnitrak.catalog_service.infrastructure.adapters.external.tmdb.dto.TmdbSeriesDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

@Component
public class TmdbSeriesMapper {

    @Value("${external.tmdb.image-base-url:https://image.tmdb.org/t/p/w500}")
    private String imageBaseUrl;

    public SeriesItem toDomain(TmdbSeriesDto dto){
        if(dto == null) return null;

        Instant releaseDateInstant = parseDate(dto.getFirstAirDate());

        List<String> genres = (dto.getGenres() == null) ? Collections.emptyList() :
                dto.getGenres().stream().map(TmdbMovieDto.TmdbGenreDto::getName).toList();

        List<String> networks = (dto.getNetworks() == null) ? Collections.emptyList() :
                dto.getNetworks().stream().map(TmdbSeriesDto.TmdbNetworkDto::getName).toList();

        List<Season> seasons = (dto.getSeasons() == null) ? Collections.emptyList() :
                dto.getSeasons().stream().map(this::toSeasonDomain).toList();

        return SeriesItem.builder()
                .title(dto.getName())
                .originalTitle(dto.getOriginalName())
                .alternativeTitles(Collections.emptyList())
                .synopsis(dto.getOverview())
                .posterUrl(dto.getPosterPath() != null ? imageBaseUrl + dto.getPosterPath() : null)
                .bannerUrl(dto.getBackdropPath() != null ? imageBaseUrl + dto.getBackdropPath() : null)
                .releaseDate(releaseDateInstant)
                .genres(genres)
                .type(MediaType.SERIES)
                .status(mapStatus(dto.getStatus()))
                .externalProvider("TMDB")
                .externalId(dto.getId())
                .lastSyncedAt(Instant.now())
                .syncStatus(SyncStatus.SYNCED)
                .totalSeasons(dto.getNumberOfSeasons())
                .totalEpisodes(dto.getNumberOfEpisodes())
                .networks(networks)
                .inProduction(dto.getInProduction())
                .seasons(seasons)
                .build();
    }

    private Season toSeasonDomain(TmdbSeriesDto.TmdbSeasonSummaryDto dto) {
        if (dto == null) return null;

        LocalDate localAirDate = null;
        if (dto.getAirDate() != null && !dto.getAirDate().isBlank()) {
            try {
                localAirDate = LocalDate.parse(dto.getAirDate());
            } catch (Exception ignored) {
                localAirDate = null;
            }
        }

        return Season.builder()
                .seasonNumber(dto.getSeasonNumber() != null ? dto.getSeasonNumber() : 0)
                .name(dto.getName())
                .overview(dto.getOverview())
                .posterUrl(dto.getPosterPath() != null ? imageBaseUrl + dto.getPosterPath() : null)
                .totalEpisodes(dto.getEpisodeCount())
                .airDate(localAirDate)
                .episodes(Collections.emptyList())
                .build();
    }

    private Instant parseDate(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) return null;
        try {
            return LocalDate.parse(dateStr).atStartOfDay().toInstant(ZoneOffset.UTC);
        } catch (Exception e) {
            return null;
        }
    }

    private ReleaseStatus mapStatus(String tmdbStatus) {
        if (tmdbStatus == null) return ReleaseStatus.UPCOMING;
        return switch (tmdbStatus.toLowerCase()) {
            case "returning series" -> ReleaseStatus.ONGOING;
            case "ended" -> ReleaseStatus.RELEASED;
            case "in production", "planned" -> ReleaseStatus.UPCOMING;
            case "canceled" -> ReleaseStatus.CANCELLED;
            default -> ReleaseStatus.UPCOMING;
        };
    }

}
