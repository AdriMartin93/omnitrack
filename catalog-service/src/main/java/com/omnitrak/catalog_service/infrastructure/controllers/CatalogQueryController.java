package com.omnitrak.catalog_service.infrastructure.controllers;


import com.omnitrak.catalog_service.domain.models.enums.MediaType;
import com.omnitrak.catalog_service.domain.models.media.MediaItem;
import com.omnitrak.catalog_service.domain.models.values.CatalogFilter;
import com.omnitrak.catalog_service.domain.ports.in.query.*;
import com.omnitrak.catalog_service.infrastructure.controllers.dto.CatalogFilterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
public class CatalogQueryController {

    private final GetMediaDetailsUseCase getMediaDetailsUseCase;
    private final SearchCatalogUseCase searchCatalogUseCase;
    private final SearchExternalMediaUseCase searchExternalMediaUseCase;
    private final FilterCatalogUseCase filterCatalogUseCase;
    private final GetUpcomingReleaseUseCase getUpcomingReleaseUseCase;
    private final GetMediaByMonthUseCase getMediaByMonthUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<MediaItem> getById(@PathVariable String id) {
        return getMediaDetailsUseCase.execute(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<MediaItem>> search(
            @RequestParam String query,
            @RequestParam(required = false) MediaType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        List<MediaItem> results = searchCatalogUseCase.execute(query, type, page, size);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/search/external")
    public ResponseEntity<List<? extends MediaItem>> searchExternal(
            @RequestParam String query,
            @RequestParam MediaType type
    ) {
        List<? extends MediaItem> results = searchExternalMediaUseCase.execute(query, type);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<MediaItem>> filter(CatalogFilterRequest request) {
        List<MediaItem> results = filterCatalogUseCase.execute(request.toDomain());
        return ResponseEntity.ok(results);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<MediaItem>> getUpcoming(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to
    ) {
        List<MediaItem> results = getUpcomingReleaseUseCase.execute(from, to);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/schedule/{year}/{month}")
    public ResponseEntity<List<MediaItem>> getByMonth(
            @PathVariable int year,
            @PathVariable int month,
            @RequestParam MediaType type
    ) {
        List<MediaItem> results = getMediaByMonthUseCase.execute(year, month, type);
        return ResponseEntity.ok(results);
    }
}
