package com.omnitrak.catalog_service.application.query;

import com.omnitrak.catalog_service.domain.annotations.UseCase;
import com.omnitrak.catalog_service.domain.models.enums.MediaType;
import com.omnitrak.catalog_service.domain.models.media.MediaItem;
import com.omnitrak.catalog_service.domain.ports.in.query.GetMediaByMonthUseCase;
import com.omnitrak.catalog_service.domain.ports.out.persistence.CatalogPersistencePort;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetMediaByMonthUseCaseImpl implements GetMediaByMonthUseCase {
    private final CatalogPersistencePort persistencePort;

    @Override
    public List<MediaItem> execute(int year, int month, MediaType type){

        YearMonth yearMonth = YearMonth.of(year,month);

        Instant startOfMonth = yearMonth.atDay(1)
                .atStartOfDay()
                .toInstant(ZoneOffset.UTC);

        Instant endOfMonth = yearMonth.atEndOfMonth()
                .atTime(23, 59, 59, 999_999_999)
                .toInstant(ZoneOffset.UTC);

        return persistencePort.findByTypeAndReleaseDateBetween(type, startOfMonth, endOfMonth);
    }
}
