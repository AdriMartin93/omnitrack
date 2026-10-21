package com.omnitrak.catalog_service.infrastructure.repositories;

import com.omnitrak.catalog_service.domain.models.enums.MediaType;
import com.omnitrak.catalog_service.infrastructure.entities.MediaDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Repository
public interface MongoCatalogRepository extends MongoRepository<MediaDocument, String> {

    Optional<MediaDocument> findByExternalProviderAndExternalId(String externalProvider, Long externalId);

    boolean existsById(String id); //el id interno

    List<MediaDocument> findByReleaseDateBetweenOrderByReleaseDateAsc(Instant from, Instant to);

    List<MediaDocument> findByTypeAndReleaseDateBetweenOrderByReleaseDateAsc(MediaType type, Instant from, Instant to);

    @Query("{ 'type': ?1, $or: [ { 'title': { $regex: ?0, $options: 'i' } }, { 'originalTitle': { $regex: ?0, $options: 'i' } }, { 'alternativeTitles': { $regex: ?0, $options: 'i' } } ] }")
    Page<MediaDocument> searchByTextAndType(String queryRegex, MediaType type, Pageable pageable);

    @Query("{ $or: [ { 'title': { $regex: ?0, $options: 'i' } }, { 'originalTitle': { $regex: ?0, $options: 'i' } }, { 'alternativeTitles': { $regex: ?0, $options: 'i' } } ] }")
    Page<MediaDocument> searchByText(String queryRegex, Pageable pageable);
}
