package com.omnitrak.catalog_service.domain.models.media;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class VideoGameItem extends MediaItem {

    private final String developer;
    private final String publisher;
    private final List<String> platforms;
}
