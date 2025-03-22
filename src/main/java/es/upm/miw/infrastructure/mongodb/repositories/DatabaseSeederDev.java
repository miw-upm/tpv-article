package es.upm.miw.infrastructure.mongodb.repositories;


import es.upm.miw.domain.model.Tax;
import es.upm.miw.infrastructure.mongodb.entities.ArticleEntity;
import es.upm.miw.infrastructure.mongodb.entities.ProviderEntity;
import es.upm.miw.infrastructure.mongodb.entities.TagEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@Profile({"dev", "test"})
public class DatabaseSeederDev {
    private final ProviderRepository providerRepository;
    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;

    private final DatabaseStarting databaseStarting;

    public DatabaseSeederDev(ProviderRepository providerRepository, ArticleRepository articleRepository, TagRepository tagRepository, DatabaseStarting databaseStarting) {
        this.providerRepository = providerRepository;
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
        this.databaseStarting = databaseStarting;
        this.deleteAllAndInitializeAndSeedDataBase();
    }

    public void deleteAllAndInitializeAndSeedDataBase() {
        this.deleteAllAndInitialize();
        this.seedDataBaseJava();
    }

    private void deleteAllAndInitialize() {
        this.tagRepository.deleteAll();
        this.articleRepository.deleteAll();
        this.providerRepository.deleteAll();
        log.warn("------- Delete All -----------");
        this.databaseStarting.initialize();
    }

    private void seedDataBaseJava() {
        log.warn("------- Initial Load from JAVA ---------------------------------------------------------------");
        ProviderEntity[] providers = {
                ProviderEntity.builder().id(UUID.randomUUID()).company("pro1").nif("12345678b").phone("9166666601")
                        .address("C/TPV-pro, 1").email("p1@gmail.com").note("p1").active(true).build(),
                ProviderEntity.builder().id(UUID.randomUUID()).company("pro2").nif("12345678z").phone("9166666602")
                        .address("C/TPV-pro, 2").email("p2@gmail.com").active(false).build(),
                ProviderEntity.builder().id(UUID.randomUUID()).company("pro3").nif("12345678e").phone("9166666603")
                        .address("C/TPV-pro, 3").email("p2@gmail.com").note("p3").active(true).build(),
                ProviderEntity.builder().id(UUID.randomUUID()).company("pro4").nif("12345678h").phone("9166666604")
                        .address("C/TPV-pro, 4").email("p3@gmail.com").note("p4").active(true).build(),
        };
        this.providerRepository.saveAll(List.of(providers));
        log.warn("        ------- providers --------------------------------------------------------------------");

        ArticleEntity[] articles = {
                ArticleEntity.builder().id(UUID.randomUUID()).barcode("8400000000017").description("Zarzuela - Falda T2")
                        .retailPrice(new BigDecimal("20")).tax(Tax.GENERAL).stock(10).providerEntity(providers[0])
                        .registrationDate(LocalDateTime.now()).discontinued(false).build(),
                ArticleEntity.builder().id(UUID.randomUUID()).barcode("8400000000024").description("Zarzuela - Falda T4")
                        .retailPrice(new BigDecimal("27.8")).tax(Tax.GENERAL).stock(5).providerEntity(providers[0])
                        .registrationDate(LocalDateTime.now()).discontinued(false).build(),
                ArticleEntity.builder().id(UUID.randomUUID()).barcode("8400000000031").description("descrip-a3")
                        .retailPrice(new BigDecimal("10.12")).tax(Tax.FREE).stock(8).providerEntity(providers[0])
                        .registrationDate(LocalDateTime.now()).discontinued(false).build(),
                ArticleEntity.builder().id(UUID.randomUUID()).barcode("8400000000048").description("descrip-a4")
                        .retailPrice(new BigDecimal("0.23")).tax(Tax.REDUCED).stock(1).providerEntity(providers[0])
                        .registrationDate(LocalDateTime.now()).discontinued(false).build(),
                ArticleEntity.builder().id(UUID.randomUUID()).barcode("8400000000055").description("descrip-a5")
                        .retailPrice(new BigDecimal("0.23")).tax(Tax.SUPER_REDUCED).stock(0).providerEntity(providers[0])
                        .registrationDate(LocalDateTime.now()).discontinued(false).build(),
                ArticleEntity.builder().id(UUID.randomUUID()).barcode("8400000000062").description("descrip-a6")
                        .retailPrice(new BigDecimal("0.01")).tax(Tax.REDUCED).stock(0).providerEntity(providers[1])
                        .registrationDate(LocalDateTime.now()).discontinued(true).build(),
                ArticleEntity.builder().id(UUID.randomUUID()).barcode("8400000000079").description("Zarzuela - Polo T2")
                        .retailPrice(new BigDecimal("16")).tax(Tax.GENERAL).stock(10).providerEntity(providers[0])
                        .registrationDate(LocalDateTime.now()).discontinued(false).build(),
                ArticleEntity.builder().id(UUID.randomUUID()).barcode("8400000000086").description("Zarzuela - Polo T4")
                        .retailPrice(new BigDecimal("17.8")).tax(Tax.SUPER_REDUCED).stock(5).providerEntity(providers[0])
                        .registrationDate(LocalDateTime.now()).discontinued(false).build(),
                ArticleEntity.builder().id(UUID.randomUUID()).barcode("8400000000100").description("without provider")
                        .retailPrice(new BigDecimal("0.12")).tax(Tax.FREE).stock(5).registrationDate(LocalDateTime.now())
                        .discontinued(false).build(),
        };
        this.articleRepository.saveAll(List.of(articles));
        log.warn("        ------- articles ---------------------------------------------------------------------");
        TagEntity[] tags = {
                TagEntity.builder().id(UUID.randomUUID()).name("tag1").group("group1").description("Tag 1")
                        .articleEntity(articles[0]).articleEntity(articles[1]).articleEntity(articles[2]).build(),
                TagEntity.builder().id(UUID.randomUUID()).name("tag2").group("group2").description("Tag 2")
                        .articleEntity(articles[2]).articleEntity(articles[3]).articleEntity(articles[4]).build(),
                TagEntity.builder().id(UUID.randomUUID()).name("tag3").group("group2").description("Tag 3")
                        .articleEntity(articles[5]).articleEntity(articles[6]).build(),
        };
        this.tagRepository.saveAll(List.of(tags));
        log.warn("        ------- tags -------------------------------------------------------------------------");

    }

}
