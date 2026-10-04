package com.omnitrak.catalog_service.domain.models.media;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class MangaItem extends MediaItem {

    private final Integer totalVolumes;
    private final Integer totalChapters;
    private final List<String> authors;
    private final String serialization;
    private final Long externalMalId;
}
