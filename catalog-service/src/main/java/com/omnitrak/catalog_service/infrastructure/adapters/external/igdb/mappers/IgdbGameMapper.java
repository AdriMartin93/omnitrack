package com.omnitrak.catalog_service.infrastructure.adapters.external.igdb.mappers;


import com.omnitrak.catalog_service.domain.models.enums.ReleaseStatus;
import com.omnitrak.catalog_service.domain.models.enums.SyncStatus;
import com.omnitrak.catalog_service.domain.models.media.VideoGameItem;
import com.omnitrak.catalog_service.infrastructure.adapters.external.igdb.dto.IgdbGameDto;
import com.omnitrak.catalog_service.domain.models.enums.MediaType;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class IgdbGameMapper {

    public VideoGameItem toDomain(IgdbGameDto dto) {
        if (dto == null) return null;

        Instant releaseDate = dto.getFirstReleaseDate() != null
                ? Instant.ofEpochSecond(dto.getFirstReleaseDate())
                : null;

        List<String> genres = extractNames(dto.getGenres());
        List<String> platforms = extractNames(dto.getPlatforms());
        List<String> gameModes = extractNames(dto.getGameModes());

        List<String> developers = Collections.emptyList();
        List<String> publishers = Collections.emptyList();

        if (dto.getInvolvedCompanies() != null) {
            developers = dto.getInvolvedCompanies().stream()
                    .filter(ic -> Boolean.TRUE.equals(ic.getDeveloper()) && ic.getCompany() != null)
                    .map(ic -> ic.getCompany().getName())
                    .filter(Objects::nonNull)
                    .toList();

            publishers = dto.getInvolvedCompanies().stream()
                    .filter(ic -> Boolean.TRUE.equals(ic.getPublisher()) && ic.getCompany() != null)
                    .map(ic -> ic.getCompany().getName())
                    .filter(Objects::nonNull)
                    .toList();
        }

        String engine = (dto.getGameEngines() != null && !dto.getGameEngines().isEmpty())
                ? dto.getGameEngines().get(0).getName()
                : null;

        String coverUrl = null;
        if (dto.getCover() != null && dto.getCover().getUrl() != null) {
            String rawUrl = dto.getCover().getUrl();
            if (rawUrl.startsWith("//")) {
                rawUrl = "https:" + rawUrl;
            }
            coverUrl = rawUrl.replace("t_thumb", "t_cover_big");
        }

        return VideoGameItem.builder()
                // Campos base de MediaItem
                .title(dto.getName())
                .originalTitle(dto.getName())
                .alternativeTitles(Collections.emptyList())
                .synopsis(dto.getSummary())
                .posterUrl(coverUrl)
                .releaseDate(releaseDate)
                .genres(genres)
                .type(MediaType.VIDEOGAME)
                .status(mapReleaseStatus(dto.getStatus(), releaseDate))
                .externalProvider("IGDB")
                .externalId(dto.getId())
                .lastSyncedAt(Instant.now())
                .syncStatus(SyncStatus.SYNCED)
                .developers(developers)
                .publishers(publishers)
                .platforms(platforms)
                .gameModes(gameModes)
                .engine(engine)
                .build();
    }

    private List<String> extractNames(List<IgdbGameDto.IgdbNamedItemDto> items) {
        if (items == null) return Collections.emptyList();
        return items.stream()
                .map(IgdbGameDto.IgdbNamedItemDto::getName)
                .filter(Objects::nonNull)
                .toList();
    }

    private ReleaseStatus mapReleaseStatus(Integer igdbStatus, Instant releaseDate) {
        if (igdbStatus != null && igdbStatus == 6) {
            return ReleaseStatus.CANCELLED;
        }
        if (releaseDate != null && releaseDate.isAfter(Instant.now())) {
            return ReleaseStatus.UPCOMING;
        }
        return ReleaseStatus.RELEASED;
    }
}
