package com.omnitrak.catalog_service.infrastructure.mappers.strategies;

import com.omnitrak.catalog_service.domain.models.media.MediaItem;
import com.omnitrak.catalog_service.domain.models.media.MovieItem;
import com.omnitrak.catalog_service.infrastructure.entities.MediaDocument;
import com.omnitrak.catalog_service.infrastructure.entities.MovieDocument;
import org.springframework.stereotype.Component;

@Component
public class MovieDocumentMapper implements MediaItemMapperStrategy<MovieItem, MovieDocument> {

    @Override
    public boolean supportsDomain(MediaItem item) {
        return item != null && item.getClass().equals(MovieItem.class);
    }

    @Override
    public boolean supportsEntity(MediaDocument document) {
        return document != null && document.getClass().equals(MovieDocument.class);
    }

    @Override
    public MovieDocument toDocument(MovieItem movie) {
        if (movie == null) return null;

        return MovieDocument.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .originalTitle(movie.getOriginalTitle())
                .alternativeTitles(movie.getAlternativeTitles())
                .synopsis(movie.getSynopsis())
                .posterUrl(movie.getPosterUrl())
                .bannerUrl(movie.getBannerUrl())
                .releaseDate(movie.getReleaseDate())
                .genres(movie.getGenres())
                .type(movie.getType())
                .status(movie.getStatus())
                .externalProvider(movie.getExternalProvider())
                .externalId(movie.getExternalId())
                .lastSyncedAt(movie.getLastSyncedAt())
                .syncStatus(movie.getSyncStatus())
                .durationMinutes(movie.getDurationMinutes())
                .directors(movie.getDirectors())
                .productionCompanies(movie.getProductionCompanies())
                .build();
    }

    @Override
    public MovieItem toDomain(MovieDocument doc) {
        if (doc == null) return null;

        return MovieItem.builder()
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
                .durationMinutes(doc.getDurationMinutes())
                .directors(doc.getDirectors())
                .productionCompanies(doc.getProductionCompanies())
                .build();
    }
}