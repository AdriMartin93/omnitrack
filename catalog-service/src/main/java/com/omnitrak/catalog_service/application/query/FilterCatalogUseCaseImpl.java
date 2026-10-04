package com.omnitrak.catalog_service.application.query;

import com.omnitrak.catalog_service.domain.annotations.UseCase;
import com.omnitrak.catalog_service.domain.models.media.MediaItem;
import com.omnitrak.catalog_service.domain.models.values.CatalogFilter;
import com.omnitrak.catalog_service.domain.ports.in.query.FilterCatalogUseCase;
import com.omnitrak.catalog_service.domain.ports.out.persistence.CatalogPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.annotation.SendToUser;

import java.util.Collections;
import java.util.List;


@UseCase
@RequiredArgsConstructor
public class FilterCatalogUseCaseImpl implements FilterCatalogUseCase {

    private final CatalogPersistencePort persistencePort;

    @Override
    public List<MediaItem> execute(CatalogFilter filter){

        if(filter == null){
            return Collections.emptyList();
        }

        return persistencePort.findByCriteria(filter);
    }

}
