package es.upm.miw.infrastructure.mongodb.persistence;

import es.upm.miw.domain.exceptions.NotFoundException;
import es.upm.miw.domain.model.Tag;
import es.upm.miw.domain.persistence.TagPersistence;
import es.upm.miw.infrastructure.mongodb.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class TagPersistenceMongodb implements TagPersistence {

    private final TagRepository tagRepository;

    @Autowired
    public TagPersistenceMongodb(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag read(UUID id) {
        return this.tagRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Non existent id: " + id))
                .toTag();
    }

    @Override
    public Tag readByName(String name) {
        return this.tagRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Non existent name: " + name))
                .toTag();
    }
}
