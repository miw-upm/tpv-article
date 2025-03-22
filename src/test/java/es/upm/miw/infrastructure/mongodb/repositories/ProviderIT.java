package es.upm.miw.infrastructure.mongodb.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ProviderIT {

    @Autowired
    private ProviderRepository providerRepository;

    @Test
    void test(){
        System.out.println(this.providerRepository.findAll());
    }
}
