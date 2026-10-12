package com.omnitrak.catalog_service.domain.models.media;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class VideoGameItem extends MediaItem {

    private final List<String> developers;
    private final List<String> publishers;
    private final List<String> platforms;
    private final List<String> gameModes; //esto se refiere a 1 o 2 jugadores, cooperativo...
    private final String engine; //por ejemplo Unreal Engine, RE engine..
}
