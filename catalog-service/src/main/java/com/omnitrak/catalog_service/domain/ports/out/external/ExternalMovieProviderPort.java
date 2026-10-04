package com.omnitrak.catalog_service.domain.ports.out.external;

import com.omnitrak.catalog_service.domain.models.media.MovieItem;

import java.util.List;
import java.util.Optional;

public interface ExternalMovieProviderPort {

    Optional<MovieItem> fetchMovieById(Long tmdbId);
    List<MovieItem> searchMoviesByTitle(String query);
}
