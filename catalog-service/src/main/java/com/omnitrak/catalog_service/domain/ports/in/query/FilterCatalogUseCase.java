package com.omnitrak.catalog_service.domain.ports.in.query;

import com.omnitrak.catalog_service.domain.models.values.CatalogFilter;
import com.omnitrak.catalog_service.domain.models.media.MediaItem;

import java.util.List;

public interface FilterCatalogUseCase {

    List<MediaItem> execute(CatalogFilter filter);
}
