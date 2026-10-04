package com.omnitrak.catalog_service.application.query;

import com.omnitrak.catalog_service.domain.annotations.UseCase;
import com.omnitrak.catalog_service.domain.models.media.MediaItem;
import com.omnitrak.catalog_service.domain.ports.in.query.GetUpcomingReleaseUseCase;
import com.omnitrak.catalog_service.domain.ports.out.persistence.CatalogPersistencePort;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


@UseCase
@RequiredArgsConstructor
public class GetUpcomingReleasesUseCaseImpl implements GetUpcomingReleaseUseCase {

    private final CatalogPersistencePort persistencePort;

    @Override
    public List<MediaItem> execute(Instant from, Instant to){

        Instant effectiveFrom = (from != null) ? from : Instant.now();
        Instant effectiveTo = (to != null) ? to : effectiveFrom.plus(30, ChronoUnit.DAYS);

        return persistencePort.findUpcoming(effectiveFrom, effectiveTo);
    }
}
