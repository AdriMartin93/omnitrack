package com.omnitrak.catalog_service.infrastructure.mappers.episodic;


import com.omnitrak.catalog_service.domain.models.episodic.Episode;
import com.omnitrak.catalog_service.infrastructure.entities.EpisodeDocument;
import org.springframework.stereotype.Component;

@Component
public class EpisodeDocumentMapper {

    public EpisodeDocument toDocument(Episode episode){
        if(episode == null) return null;
        return EpisodeDocument.builder()
                .episodeNumber(episode.getEpisodeNumber())
                .title(episode.getTitle())
                .synopsis(episode.getSynopsis())
                .airDate(episode.getAirDate())
                .durationMinutes(episode.getDurationMinutes())
                .thumbnailImageUrl(episode.getThumbnailImageUrl())
                .build();
    }

    public Episode toDomain(EpisodeDocument doc) {

        if(doc == null) return null;
        return Episode.builder()
                .episodeNumber(doc.getEpisodeNumber())
                .title(doc.getTitle())
                .synopsis(doc.getSynopsis())
                .airDate(doc.getAirDate())
                .durationMinutes(doc.getDurationMinutes())
                .thumbnailImageUrl(doc.getThumbnailImageUrl())
                .build();
    }

}
