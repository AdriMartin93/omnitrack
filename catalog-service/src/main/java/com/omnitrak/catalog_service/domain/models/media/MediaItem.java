package com.omnitrak.catalog_service.domain.models.media;


import com.omnitrak.catalog_service.domain.models.enums.SyncStatus;
import com.omnitrak.catalog_service.domain.models.values.TimeRemaining;
import com.omnitrak.catalog_service.domain.models.enums.MediaType;
import com.omnitrak.catalog_service.domain.models.enums.ReleaseStatus;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import java.time.Instant;
import java.util.List;

@Getter
@SuperBuilder
public abstract class MediaItem {
    protected final String id;
    protected final String title;
    protected final String originalTitle;
    protected List<String> alternativeTitles;

    protected final String synopsis;
    protected final String posterUrl;
    protected final String bannerUrl;
    protected final Instant releaseDate;
    protected final List<String> genres;
    protected final MediaType type;
    protected final ReleaseStatus status;

    protected final String externalProvider;
    protected final Long externalId;
    protected final Instant lastSyncedAt;
    protected final SyncStatus syncStatus;

    public TimeRemaining getTimeUntilRelease() {
        return new TimeRemaining(this.releaseDate, Instant.now());
    }

}
