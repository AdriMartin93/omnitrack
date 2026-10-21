package com.omnitrak.catalog_service.infrastructure.controllers;


import com.omnitrak.catalog_service.domain.models.media.*;
import com.omnitrak.catalog_service.domain.ports.in.sync.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/catalog/sync")
@RequiredArgsConstructor
public class CatalogSyncController {

    private final SyncMovieFromExternalProviderUseCase syncMovieUseCase;
    private final SyncSeriesFromExternalProviderUseCase syncSeriesUseCase;
    private final SyncGameFromExternalProviderUseCase syncGameUseCase;
    private final SyncAnimeFromExternalProviderUseCase syncAnimeUseCase;
    private final SyncMangaFromExternalProviderUseCase syncMangaUseCase;

    @PostMapping("/movie/{tmdbId}")
    public ResponseEntity<MovieItem> syncMovie(@PathVariable Long tmdbId) {
        MovieItem syncedMovie = syncMovieUseCase.execute(tmdbId);
        return ResponseEntity.status(HttpStatus.CREATED).body(syncedMovie);
    }

    @PostMapping("/series/{tmdbId}")
    public ResponseEntity<SeriesItem> syncSeries(@PathVariable Long tmdbId) {
        SeriesItem syncedSeries = syncSeriesUseCase.execute(tmdbId);
        return ResponseEntity.status(HttpStatus.CREATED).body(syncedSeries);
    }

    @PostMapping("/game/{igdbId}")
    public ResponseEntity<VideoGameItem> syncGame(@PathVariable Long igdbId) {
        VideoGameItem syncedGame = syncGameUseCase.execute(igdbId);
        return ResponseEntity.status(HttpStatus.CREATED).body(syncedGame);
    }

    @PostMapping("/anime/{malId}")
    public ResponseEntity<AnimeItem> syncAnime(@PathVariable Long malId) {
        AnimeItem syncedAnime = syncAnimeUseCase.execute(malId);
        return ResponseEntity.status(HttpStatus.CREATED).body(syncedAnime);
    }

    @PostMapping("/manga/{malId}")
    public ResponseEntity<MangaItem> syncManga(@PathVariable Long malId) {
        MangaItem syncedManga = syncMangaUseCase.execute(malId);
        return ResponseEntity.status(HttpStatus.CREATED).body(syncedManga);
    }


}
