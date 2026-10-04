package com.omnitrak.catalog_service.domain.ports.out.external;

import com.omnitrak.catalog_service.domain.models.media.MangaItem;

import java.util.List;
import java.util.Optional;

public interface ExternalMangaProviderPort {

    Optional<MangaItem> fetchMangaById(Long malId);
    List<MangaItem> searchMangaByTitle(String query);
}
