package es.upm.miw.infrastructure.mongodb.repositories;

import es.upm.miw.infrastructure.mongodb.entities.ProviderEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Log4j2
@Repository
public class DatabaseStarting {
    private static final String VARIOUS_CODE = "1";
    private static final String VARIOUS_NAME = "Various";
    private static final String VARIOUS_PHONE = "000000000";

    private final ProviderRepository providerRepository;

    @Autowired
    public DatabaseStarting(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
        this.initialize();
    }

    void initialize() {
        if (!this.providerRepository.existsByCompany(VARIOUS_CODE)) {
            this.providerRepository.save(ProviderEntity.builder().id(UUID.randomUUID()).company(VARIOUS_NAME)
                    .nif(VARIOUS_NAME).phone(VARIOUS_PHONE).active(true).build());
            log.warn("------- Create Provider Various -----------");
        }
    }

}
