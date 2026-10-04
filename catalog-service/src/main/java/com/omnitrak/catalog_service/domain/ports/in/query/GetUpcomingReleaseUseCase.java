package com.omnitrak.catalog_service.domain.ports.in.query;

import com.omnitrak.catalog_service.domain.models.media.MediaItem;

import java.time.Instant;
import java.util.List;

public interface GetUpcomingReleaseUseCase {
    List<MediaItem> execute(Instant from, Instant to);
}
