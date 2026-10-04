package com.omnitrak.catalog_service.domain.models.media;

import com.omnitrak.catalog_service.domain.models.episodic.Episode;
import com.omnitrak.catalog_service.domain.models.episodic.Season;
import com.omnitrak.catalog_service.domain.models.values.TimeRemaining;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Getter
@SuperBuilder
public class SeriesItem extends MediaItem {

    private final List<Season> seasons;
    private final Integer totalEpisodes;


    public Optional<Episode> getNextUpcomingEpisode() {
        if(seasons == null) return Optional.empty();
        Instant now = Instant.now();

        return seasons.stream()
                .filter(s -> s.getEpisodes() != null)
                .flatMap(s -> s.getEpisodes().stream())
                .filter(ep -> ep.getAirDate() != null && ep.getAirDate().isAfter(now))
                .min(java.util.Comparator.comparing(Episode::getAirDate));
    }

    public Optional<TimeRemaining> getTimeUntilNextEpisode() {
        return getNextUpcomingEpisode()
                .map(ep -> new TimeRemaining(ep.getAirDate(), Instant.now()));
    }
}
