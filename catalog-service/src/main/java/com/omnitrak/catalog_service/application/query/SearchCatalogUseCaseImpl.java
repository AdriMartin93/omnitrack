package com.omnitrak.catalog_service.application.query;

import com.omnitrak.catalog_service.domain.annotations.UseCase;
import com.omnitrak.catalog_service.domain.models.enums.MediaType;
import com.omnitrak.catalog_service.domain.models.media.MediaItem;
import com.omnitrak.catalog_service.domain.ports.in.query.SearchCatalogUseCase;
import com.omnitrak.catalog_service.domain.ports.out.persistence.CatalogPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class SearchCatalogUseCaseImpl implements SearchCatalogUseCase {

    private final CatalogPersistencePort persistencePort;

    @Override
    public List<MediaItem> execute(String query, MediaType type,int page, int size){
        if(query == null || query.trim().isEmpty()){
            return Collections.emptyList();
        }

        int sanitizedPage = Math.max(page, 0);
        int sanitizedSize = (size <= 0 || size > 100) ? 20 : size;

        return persistencePort.search(query.trim(), type, sanitizedPage, sanitizedSize);
    }
}
