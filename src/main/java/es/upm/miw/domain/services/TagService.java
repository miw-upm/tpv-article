package es.upm.miw.domain.services;

import es.upm.miw.domain.model.Tag;
import es.upm.miw.domain.persistence.TagPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TagService {
    private final TagPersistence tagPersistence;

    @Autowired
    public TagService(TagPersistence tagPersistence) {
        this.tagPersistence = tagPersistence;
    }

    public Tag read(UUID id) {
        return this.tagPersistence.read(id);
    }

    public Tag readByName(String name) {
        return this.tagPersistence.readByName(name);
    }
}
