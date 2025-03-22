package es.upm.miw.domain.persistence;

import es.upm.miw.domain.model.Tag;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TagPersistence {
    Tag read(UUID id);

    Tag readByName(String name);
}
