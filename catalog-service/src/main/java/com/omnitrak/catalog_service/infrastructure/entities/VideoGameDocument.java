package com.omnitrak.catalog_service.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.util.List;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true) //comparamos id del apdre y titulo del hijo, sino daria error al tener 2 juegos de mimso nombre
public class VideoGameDocument extends MediaDocument {

    public static final String FIELD_PLATFORMS = "platforms";

    private List<String> developers;
    private List<String> publishers;
    private List<String> platforms;
    private List<String> gameModes; // referente a single player, coop, etc
    private String engine; // el motor grafico (unsreal engine etc)
}
