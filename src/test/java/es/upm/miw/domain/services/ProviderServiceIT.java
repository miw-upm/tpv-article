package es.upm.miw.domain.services;

import es.upm.miw.domain.exceptions.ConflictException;
import es.upm.miw.domain.model.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
class ProviderServiceIT {

    @Autowired
    private ProviderService providerService;

    @Test
    void testCreateSuccess() {
        Provider provider = Provider.builder().company("company1").nif("nif1").phone("000111001").build();

        assertThat(providerService.create(provider))
                .isNotNull()
                .satisfies(providerBd -> {
                    assertThat(providerBd.getCompany()).isEqualTo("company1");
                    assertThat(providerBd.getNif()).isEqualTo("nif1");
                    assertThat(providerBd.getPhone()).isEqualTo("000111001");
                });
    }

    @Test
    void testCreateConflictCompany() {
        Provider provider = Provider.builder().company("pro1").build();
        assertThatThrownBy(() ->
                providerService.create(provider))
                .isInstanceOf(ConflictException.class);
    }

    @Test
    void testCreateConflictNif() {
        Provider provider = Provider.builder().nif("12345678z").build();
        assertThatThrownBy(() ->
                providerService.create(provider))
                .isInstanceOf(ConflictException.class);
    }

}
