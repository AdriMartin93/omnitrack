package com.omnitrak.catalog_service.infrastructure.mappers.strategies;


import com.omnitrak.catalog_service.domain.models.media.MediaItem;
import com.omnitrak.catalog_service.domain.models.media.VideoGameItem;
import com.omnitrak.catalog_service.infrastructure.entities.MediaDocument;
import com.omnitrak.catalog_service.infrastructure.entities.VideoGameDocument;
import org.springframework.stereotype.Component;

@Component
public class VideoGameDocumentMapper implements MediaItemMapperStrategy<VideoGameItem, VideoGameDocument> {

    @Override
    public boolean supportsDomain(MediaItem item) {
        return item != null && item.getClass().equals(VideoGameItem.class);
    }

    @Override
    public boolean supportsEntity(MediaDocument document) {
        return document != null && document.getClass().equals(VideoGameDocument.class);
    }

    @Override
    public VideoGameDocument toDocument(VideoGameItem game) {
        if (game == null) return null;

        return VideoGameDocument.builder()
                .id(game.getId())
                .title(game.getTitle())
                .originalTitle(game.getOriginalTitle())
                .alternativeTitles(game.getAlternativeTitles())
                .synopsis(game.getSynopsis())
                .posterUrl(game.getPosterUrl())
                .bannerUrl(game.getBannerUrl())
                .releaseDate(game.getReleaseDate())
                .genres(game.getGenres())
                .type(game.getType())
                .status(game.getStatus())
                .externalProvider(game.getExternalProvider())
                .externalId(game.getExternalId())
                .lastSyncedAt(game.getLastSyncedAt())
                .syncStatus(game.getSyncStatus())
                .developers(game.getDevelopers())
                .publishers(game.getPublishers())
                .platforms(game.getPlatforms())
                .gameModes(game.getGameModes())
                .engine(game.getEngine())
                .build();
    }

    @Override
    public VideoGameItem toDomain(VideoGameDocument doc) {
        if (doc == null) return null;

        return VideoGameItem.builder()
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
                .developers(doc.getDevelopers())
                .publishers(doc.getPublishers())
                .platforms(doc.getPlatforms())
                .gameModes(doc.getGameModes())
                .engine(doc.getEngine())
                .build();
    }
}
