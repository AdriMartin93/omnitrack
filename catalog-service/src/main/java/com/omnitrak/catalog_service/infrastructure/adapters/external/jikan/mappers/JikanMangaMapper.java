package com.omnitrak.catalog_service.infrastructure.adapters.external.jikan.mappers;

import com.omnitrak.catalog_service.domain.models.enums.MediaType;
import com.omnitrak.catalog_service.domain.models.enums.ReleaseStatus;
import com.omnitrak.catalog_service.domain.models.enums.SyncStatus;
import com.omnitrak.catalog_service.domain.models.media.MangaItem;
import com.omnitrak.catalog_service.infrastructure.adapters.external.jikan.dto.JikanCommonDtos.JikanNamedDto;
import com.omnitrak.catalog_service.infrastructure.adapters.external.jikan.dto.JikanMangaDto;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class JikanMangaMapper {

    public MangaItem toDomain(JikanMangaDto dto) {
        if (dto == null) return null;

        List<String> genres = extractNames(dto.getGenres());
        List<String> authors = extractNames(dto.getAuthors());

        // Primera revista/editorial de serialización
        String serialization = (dto.getSerializations() != null && !dto.getSerializations().isEmpty())
                ? dto.getSerializations().get(0).getName()
                : null;

        // Primera demografía asignada (e.g., Shounen, Seinen, Shoujo)
        String demographic = (dto.getDemographics() != null && !dto.getDemographics().isEmpty())
                ? dto.getDemographics().get(0).getName()
                : null;

        // Portada
        String posterUrl = null;
        if (dto.getImages() != null) {
            if (dto.getImages().getWebp() != null && dto.getImages().getWebp().getLargeImageUrl() != null) {
                posterUrl = dto.getImages().getWebp().getLargeImageUrl();
            } else if (dto.getImages().getJpg() != null) {
                posterUrl = dto.getImages().getJpg().getLargeImageUrl();
            }
        }

        Instant releaseDate = null;
        if (dto.getPublished() != null && dto.getPublished().getFrom() != null) {
            try {
                releaseDate = Instant.parse(dto.getPublished().getFrom());
            } catch (Exception ignored) {
                releaseDate = null;
            }
        }

        return MangaItem.builder()
                // Campos de MediaItem
                .title(dto.getTitle())
                .originalTitle(dto.getTitleJapanese())
                .alternativeTitles(Collections.emptyList())
                .synopsis(dto.getSynopsis())
                .posterUrl(posterUrl)
                .bannerUrl(null)
                .releaseDate(releaseDate)
                .genres(genres)
                .type(MediaType.MANGA)
                .status(mapReleaseStatus(dto.getStatus()))
                .externalProvider("MAL")
                .externalId(dto.getMalId())
                .lastSyncedAt(Instant.now())
                .syncStatus(SyncStatus.SYNCED)
                // Campos propios de MangaItem
                .totalVolumes(dto.getVolumes())
                .totalChapters(dto.getChapters())
                .authors(authors)
                .serialization(serialization)
                .demographic(demographic)
                .build();
    }

    private List<String> extractNames(List<JikanNamedDto> list) {
        if (list == null) return Collections.emptyList();
        return list.stream()
                .map(JikanNamedDto::getName)
                .filter(Objects::nonNull)
                .toList();
    }

    private ReleaseStatus mapReleaseStatus(String statusStr) {
        if (statusStr == null) return ReleaseStatus.UPCOMING;
        return switch (statusStr.toLowerCase()) {
            case "publishing" -> ReleaseStatus.ONGOING;
            case "finished" -> ReleaseStatus.RELEASED;
            case "discontinued" -> ReleaseStatus.CANCELLED;
            case "on hiatus" -> ReleaseStatus.ONGOING;
            default -> ReleaseStatus.UPCOMING;
        };
    }
}