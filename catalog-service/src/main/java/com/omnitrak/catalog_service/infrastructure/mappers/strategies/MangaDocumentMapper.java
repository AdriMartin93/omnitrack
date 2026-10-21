package com.omnitrak.catalog_service.infrastructure.mappers.strategies;


import com.omnitrak.catalog_service.domain.models.media.MangaItem;
import com.omnitrak.catalog_service.domain.models.media.MediaItem;
import com.omnitrak.catalog_service.infrastructure.entities.MangaDocument;
import com.omnitrak.catalog_service.infrastructure.entities.MediaDocument;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MangaDocumentMapper implements MediaItemMapperStrategy<MangaItem, MangaDocument>{

    @Override
    public boolean supportsDomain(MediaItem item){
        return item instanceof MangaItem;
    }

    @Override
    public boolean supportsEntity(MediaDocument document){
        return document instanceof MangaDocument;
    }

    public MangaDocument toDocument(MangaItem manga){

        if(manga == null) return null;

        return MangaDocument.builder()
                .id(manga.getId())
                .title(manga.getTitle())
                .originalTitle(manga.getOriginalTitle())
                .alternativeTitles(manga.getAlternativeTitles())
                .synopsis(manga.getSynopsis())
                .posterUrl(manga.getPosterUrl())
                .bannerUrl(manga.getBannerUrl())
                .releaseDate(manga.getReleaseDate())
                .genres(manga.getGenres())
                .type(manga.getType())
                .status(manga.getStatus())
                .externalProvider(manga.getExternalProvider())
                .externalId(manga.getExternalId())
                .lastSyncedAt(manga.getLastSyncedAt())
                .syncStatus(manga.getSyncStatus())
                .totalVolumes(manga.getTotalVolumes())
                .totalChapters(manga.getTotalChapters())
                .authors(manga.getAuthors())
                .serialization(manga.getSerialization())
                .demographic(manga.getDemographic())
                .build();
    }

    public MangaItem toDomain(MangaDocument manga){
        if(manga == null) return null;

        return MangaItem.builder()
                .id(manga.getId())
                .title(manga.getTitle())
                .originalTitle(manga.getOriginalTitle())
                .alternativeTitles(manga.getAlternativeTitles())
                .synopsis(manga.getSynopsis())
                .posterUrl(manga.getPosterUrl())
                .bannerUrl(manga.getBannerUrl())
                .releaseDate(manga.getReleaseDate())
                .genres(manga.getGenres())
                .type(manga.getType())
                .status(manga.getStatus())
                .externalProvider(manga.getExternalProvider())
                .externalId(manga.getExternalId())
                .lastSyncedAt(manga.getLastSyncedAt())
                .syncStatus(manga.getSyncStatus())
                .totalVolumes(manga.getTotalVolumes())
                .totalChapters(manga.getTotalChapters())
                .authors(manga.getAuthors())
                .serialization(manga.getSerialization())
                .demographic(manga.getDemographic())
                .build();
    }
}
