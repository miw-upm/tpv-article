package es.upm.miw.infrastructure.mongodb.repositories;


import es.upm.miw.infrastructure.mongodb.entities.CashierEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface CashierRepository extends MongoRepository<CashierEntity, UUID> {
    Optional<CashierEntity> findFirstByOrderByOpeningDateDesc();
}
