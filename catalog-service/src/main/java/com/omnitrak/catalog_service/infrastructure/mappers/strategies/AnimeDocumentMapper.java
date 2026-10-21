package com.omnitrak.catalog_service.infrastructure.mappers.strategies;

import com.omnitrak.catalog_service.domain.models.media.AnimeItem;
import com.omnitrak.catalog_service.domain.models.media.MediaItem;
import com.omnitrak.catalog_service.infrastructure.entities.AnimeDocument;
import com.omnitrak.catalog_service.infrastructure.entities.MediaDocument;
import com.omnitrak.catalog_service.infrastructure.mappers.episodic.SeasonDocumentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;


@Component
@RequiredArgsConstructor
public class AnimeDocumentMapper implements MediaItemMapperStrategy<AnimeItem, AnimeDocument> {

    private final SeasonDocumentMapper seasonMapper;

    @Override
    public boolean supportsDomain(MediaItem item){
        return item != null && item.getClass().equals(AnimeItem.class);
    }

    @Override
    public boolean supportsEntity(MediaDocument document){
        return document != null && document.getClass().equals(AnimeDocument.class);
    }

    @Override
    public AnimeDocument toDocument(AnimeItem anime){
        return AnimeDocument.builder()
                .id(anime.getId())
                .title(anime.getTitle())
                .originalTitle(anime.getOriginalTitle())
                .alternativeTitles(anime.getAlternativeTitles())
                .synopsis(anime.getSynopsis())
                .posterUrl(anime.getPosterUrl())
                .bannerUrl(anime.getBannerUrl())
                .releaseDate(anime.getReleaseDate())
                .genres(anime.getGenres())
                .type(anime.getType())
                .status(anime.getStatus())
                .externalProvider(anime.getExternalProvider())
                .externalId(anime.getExternalId())
                .lastSyncedAt(anime.getLastSyncedAt())
                .syncStatus(anime.getSyncStatus())
                .seasons(anime.getSeasons() == null ? Collections.emptyList() :
                        anime.getSeasons().stream().map(seasonMapper::toDocument).toList())
                .totalSeasons(anime.getTotalSeasons())
                .totalEpisodes(anime.getTotalEpisodes())
                .networks(anime.getNetworks())
                .inProduction(anime.getInProduction())
                .studios(anime.getStudios())
                .source(anime.getSource())
                .seasonYear(anime.getSeasonYear())
                .season(anime.getSeason())
                .episodeDurationMinutes(anime.getEpisodeDurationMinutes())
                .ageRating(anime.getAgeRating())
                .broadcastDay(anime.getBroadcastDay())
                .build();
    }

    @Override
    public AnimeItem toDomain(AnimeDocument doc) {
        return AnimeItem.builder()
                .id(doc.getId())
                .title(doc.getTitle())
                .originalTitle(doc.getOriginalTitle())
                .alternativeTitles(doc.getAlternativeTitles())
                .synopsis(doc.getSynopsis())
                .posterUrl(doc.getPosterUrl())
                .bannerUrl(doc.getBannerUrl())
                .releaseDate(doc.getReleaseDate())
                .genres(doc.getGenres())
                .type(doc.getType())
                .status(doc.getStatus())
                .externalProvider(doc.getExternalProvider())
                .externalId(doc.getExternalId())
                .lastSyncedAt(doc.getLastSyncedAt())
                .syncStatus(doc.getSyncStatus())
                .seasons(doc.getSeasons() == null ? Collections.emptyList() :
                        doc.getSeasons().stream().map(seasonMapper::toDomain).toList())
                .totalSeasons(doc.getTotalSeasons())
                .totalEpisodes(doc.getTotalEpisodes())
                .networks(doc.getNetworks())
                .inProduction(doc.getInProduction())
                .studios(doc.getStudios())
                .source(doc.getSource())
                .seasonYear(doc.getSeasonYear())
                .season(doc.getSeason())
                .episodeDurationMinutes(doc.getEpisodeDurationMinutes())
                .ageRating(doc.getAgeRating())
                .broadcastDay(doc.getBroadcastDay())
                .build();
    }

}
