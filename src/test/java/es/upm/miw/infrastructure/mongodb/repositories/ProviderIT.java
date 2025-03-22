package es.upm.miw.infrastructure.mongodb.repositories;

import es.upm.miw.infrastructure.mongodb.entities.ProviderEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
class ProviderIT {

    @Autowired
    private ProviderRepository providerRepository;

    @Test
    void testFindByCompanyAndActiveIsTrueNullSave() {
        assertThat(this.providerRepository.findByCompanyAndActiveIsTrueNullSave("1"))
                .map(ProviderEntity::getCompany)
                .contains("pro1")
                .doesNotContain("pro2");

    }

    @Test
    void testFindByCompanyAndPhoneAndNoteNullSafe() {
        List<ProviderEntity> providers = this.providerRepository.findByCompanyAndPhoneAndNoteNullSafe("ro", "2", null);
        assertThat(providers)
                .isNotEmpty()
                .allMatch(provider -> provider.getCompany().contains("ro") && provider.getPhone().contains("2"));
    }
}
