package es.upm.miw.infrastructure.mongodb.persistence;

import es.upm.miw.domain.exceptions.NotFoundException;
import es.upm.miw.domain.model.Article;
import es.upm.miw.domain.persistence.ArticlePersistence;
import es.upm.miw.infrastructure.mongodb.entities.ArticleEntity;
import es.upm.miw.infrastructure.mongodb.entities.ProviderEntity;
import es.upm.miw.infrastructure.mongodb.repositories.ArticleRepository;
import es.upm.miw.infrastructure.mongodb.repositories.ProviderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.stream.Stream;

@Repository
public class ArticlePersistenceMongodb implements ArticlePersistence {

    private final ProviderRepository providerRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticlePersistenceMongodb(ProviderRepository providerRepository, ArticleRepository articleRepository) {
        this.providerRepository = providerRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public Article create(Article article) {
        ProviderEntity providerEntity = null;
        if (Objects.nonNull(article.getProvider())) {
            providerEntity = this.providerRepository.findByCompany(article.getProvider().getCompany())
                    .orElseThrow(() -> new NotFoundException("Non existent company: " + article.getProvider().getCompany()));
        }
        ArticleEntity articleEntity = new ArticleEntity(article);
        articleEntity.setProviderEntity(providerEntity);
        return this.articleRepository.save(articleEntity).toArticle();
    }

    @Override
    public Article readByBarcode(String barcode) {
        return this.articleRepository.findByBarcode(barcode)
                .orElseThrow(() -> new NotFoundException("Non existent barcode: " + barcode))
                .toArticle();
    }

    @Override
    public Article update(String barcode, Article article) {
        ArticleEntity retrieveArticle = this.articleRepository.findByBarcode(barcode).orElseThrow();
        BeanUtils.copyProperties(article, retrieveArticle);
        return this.articleRepository.save(retrieveArticle).toArticle();
    }

    @Override
    public Stream<Article> findByBarcodeAndDescriptionAndReferenceAndStockLessThanAndDiscontinuedNullSafe(
            String barcode, String description, String reference, Integer stock, Boolean discontinued) {
        return this.articleRepository.findByBarcodeAndDescriptionAndReferenceAndStockLessThanAndDiscontinuedNullSafe(
                        barcode, description, reference, stock, discontinued).stream()
                .map(ArticleEntity::toArticle);
    }

    @Override
    public Stream<Article> findByBarcodeAndNotDiscontinuedNullField(String barcode) {
        return this.articleRepository.findByBarcodeLikeAndNotDiscontinuedNullSafe(barcode).stream()
                .map(ArticleEntity::toArticle);
    }

    @Override
    public Stream<Article> findByDiscontinuedIsFalse() {
        return this.articleRepository.findByDiscontinuedIsFalse().stream()
                .map(ArticleEntity::toArticle);
    }

    @Override
    public boolean existsBarcode(String barcode) {
        return this.articleRepository.existsByBarcode(barcode);
    }

    @Override
    public Stream<Article> findByProviderIsNull() {
        return this.articleRepository.findByProviderEntityIsNull().stream()
                .map(ArticleEntity::toArticle);
    }
}
