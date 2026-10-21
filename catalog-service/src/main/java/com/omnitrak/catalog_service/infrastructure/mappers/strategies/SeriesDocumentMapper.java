package com.omnitrak.catalog_service.infrastructure.mappers.strategies;

import com.omnitrak.catalog_service.domain.models.episodic.Season;
import com.omnitrak.catalog_service.domain.models.media.MediaItem;
import com.omnitrak.catalog_service.domain.models.media.SeriesItem;
import com.omnitrak.catalog_service.infrastructure.entities.MediaDocument;
import com.omnitrak.catalog_service.infrastructure.entities.SeasonDocument;
import com.omnitrak.catalog_service.infrastructure.entities.SeriesDocument;
import com.omnitrak.catalog_service.infrastructure.mappers.episodic.SeasonDocumentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;


@Component
@RequiredArgsConstructor
public class SeriesDocumentMapper implements MediaItemMapperStrategy<SeriesItem, SeriesDocument>{


    private final SeasonDocumentMapper seasonMapper;

    @Override
    public boolean supportsDomain(MediaItem item) {
        return item != null && item.getClass().equals(SeriesItem.class);
    }

    @Override
    public boolean supportsEntity(MediaDocument document) {
        return document != null && document.getClass().equals(SeriesDocument.class);
    }

    @Override
    public SeriesDocument toDocument(SeriesItem series) {
        if (series == null) return null;

        List<SeasonDocument> seasonDocs = (series.getSeasons() == null)
                ? Collections.emptyList()
                : series.getSeasons().stream().map(seasonMapper::toDocument).toList();

        return SeriesDocument.builder()
                .id(series.getId())
                .title(series.getTitle())
                .originalTitle(series.getOriginalTitle())
                .alternativeTitles(series.getAlternativeTitles())
                .synopsis(series.getSynopsis())
                .posterUrl(series.getPosterUrl())
                .bannerUrl(series.getBannerUrl())
                .releaseDate(series.getReleaseDate())
                .genres(series.getGenres())
                .type(series.getType())
                .status(series.getStatus())
                .externalProvider(series.getExternalProvider())
                .externalId(series.getExternalId())
                .lastSyncedAt(series.getLastSyncedAt())
                .syncStatus(series.getSyncStatus())
                .seasons(seasonDocs)
                .totalSeasons(series.getTotalSeasons())
                .totalEpisodes(series.getTotalEpisodes())
                .networks(series.getNetworks())
                .inProduction(series.getInProduction())
                .build();
    }

    @Override
    public SeriesItem toDomain(SeriesDocument doc) {
        if (doc == null) return null;

        List<Season> seasons = (doc.getSeasons() == null)
                ? Collections.emptyList()
                : doc.getSeasons().stream().map(seasonMapper::toDomain).toList();

        return SeriesItem.builder()
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
                .seasons(seasons)
                .totalSeasons(doc.getTotalSeasons())
                .totalEpisodes(doc.getTotalEpisodes())
                .networks(doc.getNetworks())
                .inProduction(doc.getInProduction())
                .build();
    }
}
