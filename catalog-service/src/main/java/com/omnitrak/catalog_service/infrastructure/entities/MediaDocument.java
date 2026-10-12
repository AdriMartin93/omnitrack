package com.omnitrak.catalog_service.infrastructure.entities;


import com.omnitrak.catalog_service.domain.models.enums.MediaType;
import com.omnitrak.catalog_service.domain.models.enums.ReleaseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "media_item")
@CompoundIndexes({
        //esto para que el mismo proveedor no tenga el mismo externalId 2 veces
        @CompoundIndex(name = "unique_external_provider_id", def = "{'externalProvider': 1, 'externalId': 1}", unique = true),
        // con el media type ordeno por tipo de media, con el release date ordeno por fecha de estreno(los nuevos primero)
        @CompoundIndex(name = "idx_type_release_date", def = "{'mediaType': 1, 'releaseDate': -1}")
})
public abstract class MediaDocument {

    @Id
    private String id;

    @Indexed
    private String title;

    private String originalTitle;
    private List<String> alternativeTitles;

    private String synopsis;
    private String posterUrl;
    private String bannerUrl;
    private Instant releaseDate;
    private List<String> genres;

    @Indexed
    private MediaType type;

    @Indexed
    private ReleaseStatus status;

    private String externalProvider;
    private Long externalId;
    private Instant lastSyncedAt;
    private String syncStatus;
}
