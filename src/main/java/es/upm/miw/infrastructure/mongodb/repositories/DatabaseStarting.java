package es.upm.miw.infrastructure.mongodb.repositories;

import es.upm.miw.domain.model.Tax;
import es.upm.miw.infrastructure.mongodb.entities.ArticleEntity;
import es.upm.miw.infrastructure.mongodb.entities.CashierEntity;
import es.upm.miw.infrastructure.mongodb.entities.ProviderEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.math.BigDecimal.ZERO;

@Log4j2
@Repository
public class DatabaseStarting {
    private static final String VARIOUS_CODE = "1";
    private static final String VARIOUS_NAME = "Various";
    private static final String VARIOUS_PHONE = "000000000";

    private final ProviderRepository providerRepository;
    private final ArticleRepository articleRepository;
    private final CashierRepository cashierRepository;

    @Autowired
    public DatabaseStarting(ProviderRepository providerRepository, ArticleRepository articleRepository, CashierRepository cashierRepository) {
        this.providerRepository = providerRepository;
        this.articleRepository = articleRepository;
        this.cashierRepository = cashierRepository;
        this.initialize();
    }

    void initialize() {
        if (!this.providerRepository.existsByCompany(VARIOUS_CODE)) {
            ProviderEntity provider = this.providerRepository.save(ProviderEntity.builder().
                    id(UUID.fromString("aaaaaaa0-bbbb-cccc-dddd-eeeeffff0000")).company(VARIOUS_NAME)
                    .nif(VARIOUS_NAME).phone(VARIOUS_PHONE).note(VARIOUS_NAME).active(true).build());
            log.warn("------- Create Provider Various -----------");
            this.articleRepository.save(ArticleEntity.builder()
                    .id(UUID.randomUUID())
                    .barcode(VARIOUS_CODE)
                    .description(VARIOUS_NAME).retailPrice(new BigDecimal("100.00"))
                    .stock(1000)
                    .providerEntity(provider)
                    .registrationDate(LocalDateTime.now())
                    .tax(Tax.GENERAL)
                    .discontinued(false).build());
            log.warn("------- Create Article Various -----------");
        }

        if (this.cashierRepository.findFirstByOrderByOpeningDateDesc().isEmpty()) {
            this.cashierRepository.save(CashierEntity.builder()
                    .id(UUID.randomUUID())
                    .initialCash(ZERO).cashSales(ZERO).cardSales(ZERO).usedVouchers(ZERO)
                    .deposit(ZERO).withdrawal(ZERO).lostCash(ZERO).lostCard(ZERO)
                    .finalCash(ZERO).comment("Initial").openingDate(LocalDateTime.now()).closureDate(LocalDateTime.now())
                    .build());
            log.warn("------- Create cashierClosure -----------");
        }
    }

}
