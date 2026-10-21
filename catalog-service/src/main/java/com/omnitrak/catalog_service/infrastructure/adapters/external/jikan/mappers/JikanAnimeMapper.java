package com.omnitrak.catalog_service.infrastructure.adapters.external.jikan.mappers;


import com.omnitrak.catalog_service.domain.models.enums.AnimeSeason;
import com.omnitrak.catalog_service.domain.models.enums.MediaType;
import com.omnitrak.catalog_service.domain.models.enums.ReleaseStatus;
import com.omnitrak.catalog_service.domain.models.enums.SyncStatus;
import com.omnitrak.catalog_service.domain.models.media.AnimeItem;
import com.omnitrak.catalog_service.infrastructure.adapters.external.jikan.dto.JikanAnimeDto;
import org.springframework.stereotype.Component;
import com.omnitrak.catalog_service.infrastructure.adapters.external.jikan.dto.JikanCommonDtos.*;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JikanAnimeMapper {

    // regex para extrar la duracion que nos dan en frases en plan 23 min per ep
    private static final Pattern DURATION_PATTERN = Pattern.compile("(\\d+)\\s*min");

    public AnimeItem toDomain(JikanAnimeDto dto){

        if(dto == null) return null;

        List<String> studios = extractNames(dto.getStudios());
        List<String> genres = extractNames(dto.getGenres());

        //priorizo la imagen en formato webp en hq o recurro a jpg
        String posterUrl = null;
        if(dto.getImages() != null) {
            if(dto.getImages().getWebp() != null && dto.getImages().getWebp().getLargeImageUrl() != null){
                posterUrl = dto.getImages().getWebp().getLargeImageUrl();
            }else if(dto.getImages().getJpg()!= null){
                posterUrl = dto.getImages().getJpg().getLargeImageUrl();
            }
        }

        // parsea la fecha de inicio de emision
        Instant releaseDate = null;
        if(dto.getAired() != null && dto.getAired().getFrom() != null) {
            try {
                releaseDate = Instant.parse(dto.getAired().getFrom());
            } catch (Exception ignored){
                releaseDate = null;
            }
        }

        String broadcastDay = (dto.getBroadcast() != null) ? dto.getBroadcast().getDay() : null;

        return AnimeItem.builder()
                .title(dto.getTitle())
                .originalTitle(dto.getTitleJapanese())
                .alternativeTitles(Collections.emptyList())
                .synopsis(dto.getSynopsis())
                .posterUrl(posterUrl)
                .bannerUrl(null)
                .releaseDate(releaseDate)
                .genres(genres)
                .type(MediaType.ANIME)
                .status(mapReleaseStatus(dto.getStatus()))
                .externalProvider("MAL")
                .externalId(dto.getMalId())
                .lastSyncedAt(Instant.now())
                .syncStatus(SyncStatus.SYNCED)
                .totalEpisodes(dto.getEpisodes())
                .totalSeasons(1) // Jikan cataloga cada temporada como una ficha distinta
                .networks(Collections.emptyList())
                .inProduction("Currently Airing".equalsIgnoreCase(dto.getStatus()))
                .seasons(Collections.emptyList())
                .studios(studios)
                .source(dto.getSource())
                .seasonYear(dto.getYear())
                .season(mapSeason(dto.getSeason()))
                .episodeDurationMinutes(extractMinutes(dto.getDuration()))
                .ageRating(dto.getRating())
                .broadcastDay(broadcastDay)
                .build();
    }

    private List<String> extractNames(List<JikanNamedDto> list) {
        if (list == null) return Collections.emptyList();
        return list.stream()
                .map(JikanNamedDto::getName)
                .filter(Objects::nonNull)
                .toList();
    }

    private Integer extractMinutes(String durationStr) {
        if (durationStr == null || durationStr.isBlank()) return null;
        Matcher matcher = DURATION_PATTERN.matcher(durationStr);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return null;
    }

    private AnimeSeason mapSeason(String seasonStr) {
        if (seasonStr == null) return null;
        try {
            return AnimeSeason.valueOf(seasonStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private ReleaseStatus mapReleaseStatus(String jikanStatus) {
        if (jikanStatus == null) return ReleaseStatus.UPCOMING;
        return switch (jikanStatus.toLowerCase()) {
            case "currently airing" -> ReleaseStatus.ONGOING;
            case "finished airing" -> ReleaseStatus.RELEASED;
            case "not yet aired" -> ReleaseStatus.UPCOMING;
            default -> ReleaseStatus.UPCOMING;
        };
    }
}
