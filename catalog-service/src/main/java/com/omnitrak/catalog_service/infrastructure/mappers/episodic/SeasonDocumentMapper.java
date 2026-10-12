package com.omnitrak.catalog_service.infrastructure.mappers.episodic;


import com.omnitrak.catalog_service.domain.models.episodic.Season;
import com.omnitrak.catalog_service.infrastructure.entities.SeasonDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class SeasonDocumentMapper {

    private final EpisodeDocumentMapper episodeMapper;

    public SeasonDocument toDocument(Season season) {
        if(season == null) return null;
        return SeasonDocument.builder()
                .seasonNumber(season.getSeasonNumber())
                .name(season.getName())
                .airDate(season.getAirDate())
                .episodes(season.getEpisodes() == null ? Collections.emptyList() :
                        season.getEpisodes().stream().map(episodeMapper::toDocument).toList())
                .build();
    }

    public Season toDomain(SeasonDocument doc){
        if(doc == null) return null;
        return Season.builder()
                .seasonNumber(doc.getSeasonNumber() != null ? doc.getSeasonNumber() : 0)
                .name(doc.getName())
                .airDate(doc.getAirDate())
                .episodes(doc.getEpisodes() == null ? Collections.emptyList() :
                        doc.getEpisodes().stream().map(episodeMapper::toDomain).toList())
                .build();
    }
}
