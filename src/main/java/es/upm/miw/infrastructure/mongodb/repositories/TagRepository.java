package es.upm.miw.infrastructure.mongodb.repositories;

import es.upm.miw.infrastructure.mongodb.entities.TagEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface TagRepository extends MongoRepository<TagEntity, UUID> {
    Optional<TagEntity> findByName(String name);
}
