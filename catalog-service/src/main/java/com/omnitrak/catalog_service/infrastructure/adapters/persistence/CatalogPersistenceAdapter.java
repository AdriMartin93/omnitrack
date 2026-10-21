package com.omnitrak.catalog_service.infrastructure.adapters.persistence;

import com.omnitrak.catalog_service.domain.models.enums.MediaType;
import com.omnitrak.catalog_service.domain.models.media.MediaItem;
import com.omnitrak.catalog_service.domain.models.values.CatalogFilter;
import com.omnitrak.catalog_service.domain.ports.out.persistence.CatalogPersistencePort;
import com.omnitrak.catalog_service.infrastructure.entities.*;
import com.omnitrak.catalog_service.infrastructure.mappers.MediaDocumentMapper;
import com.omnitrak.catalog_service.infrastructure.repositories.MongoCatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class CatalogPersistenceAdapter implements CatalogPersistencePort {

    private final MongoCatalogRepository mongoRepository;
    private final MongoTemplate mongoTemplate;
    private final MediaDocumentMapper documentMapper;

    @Override
    public <T extends MediaItem> T save(T mediaItem) {
        MediaDocument document = documentMapper.toDocument(mediaItem);
        MediaDocument savedDoc = mongoRepository.save(document);
        return documentMapper.toDomain(savedDoc);
    }

    @Override
    public Optional<MediaItem> findById(String id) {
        return mongoRepository.findById(id)
                .map(documentMapper::toDomain);
    }

    @Override
    public <T extends MediaItem> Optional<T> findByExternalProviderAndExternalId(String externalProvider, Long externalId) {
        return mongoRepository.findByExternalProviderAndExternalId(externalProvider, externalId)
                .map(documentMapper::toDomain);
    }

    @Override
    public boolean existsById(String id) {
        return mongoRepository.existsById(id);
    }

    @Override
    public List<MediaItem> findUpcoming(Instant from, Instant to) {
        return mongoRepository.findByReleaseDateBetweenOrderByReleaseDateAsc(from, to)
                .stream()
                .map(doc -> (MediaItem) documentMapper.toDomain(doc))
                .toList();
    }

    @Override
    public List<MediaItem> search(String query, MediaType type, int page, int size) {
        Pageable pageable = PageRequest.of(Math.max(page, 0), size > 0 ? size : 20);

        String sanitizedQuery = Pattern.quote(query.trim());

        var pageResult = (type != null)
                ? mongoRepository.searchByTextAndType(sanitizedQuery, type, pageable)
                : mongoRepository.searchByText(sanitizedQuery, pageable);

        return pageResult.getContent()
                .stream()
                .map(doc -> (MediaItem) documentMapper.toDomain(doc))
                .toList();
    }

    @Override
    public List<MediaItem> findByTypeAndReleaseDateBetween(MediaType type, Instant from, Instant to) {
        return mongoRepository.findByTypeAndReleaseDateBetweenOrderByReleaseDateAsc(type, from, to)
                .stream()
                .map(doc -> (MediaItem) documentMapper.toDomain(doc))
                .toList();
    }

    @Override
    public List<MediaItem> findByCriteria(CatalogFilter filter) {
        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();

        if (filter.getMediaType() != null) {
            criteriaList.add(Criteria.where(MediaDocument.FIELD_TYPE).is(filter.getMediaType()));
        }

        if (filter.getGenre() != null && !filter.getGenre().isBlank()) {
            criteriaList.add(Criteria.where(MediaDocument.FIELD_GENRES).is(filter.getGenre().trim()));
        }

        if (filter.getStatus() != null) {
            criteriaList.add(Criteria.where(MediaDocument.FIELD_STATUS).is(filter.getStatus()));
        }

        if (filter.getReleaseYear() != null) {
            Instant startOfYear = LocalDate.of(filter.getReleaseYear(), 1, 1).atStartOfDay().toInstant(ZoneOffset.UTC);
            Instant endOfYear = LocalDate.of(filter.getReleaseYear(), 12, 31).atTime(23, 59, 59).toInstant(ZoneOffset.UTC);
            criteriaList.add(Criteria.where(MediaDocument.FIELD_RELEASE_DATE).gte(startOfYear).lte(endOfYear));
        }

        // Filtro específico para videojuegos
        if (filter.getPlatform() != null && !filter.getPlatform().isBlank()) {
            criteriaList.add(Criteria.where(VideoGameDocument.FIELD_PLATFORMS).is(filter.getPlatform().trim()));
        }

        // Filtro específico para mangas
        if (filter.getAuthors() != null && !filter.getAuthors().isEmpty()) {
            criteriaList.add(Criteria.where(MangaDocument.FIELD_AUTHORS).in(filter.getAuthors()));
        }

        // Filtros específicos para anime
        if (filter.getSeasonYear() != null) {
            criteriaList.add(Criteria.where(AnimeDocument.FIELD_SEASON_YEAR).is(filter.getSeasonYear()));
        }
        if (filter.getSeason() != null) {
            criteriaList.add(Criteria.where(AnimeDocument.FIELD_SEASON).is(filter.getSeason()));
        }

        // Combinar todos los filtros con operador AND si existen
        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }

        // Paginación segura
        int page = Math.max(filter.getPage(), 0);
        int size = filter.getSize() > 0 ? filter.getSize() : 20;
        query.with(PageRequest.of(page, size));

        List<MediaDocument> results = mongoTemplate.find(query, MediaDocument.class);

        return results.stream()
                .map(doc -> (MediaItem) documentMapper.toDomain(doc))
                .toList();
    }
}
