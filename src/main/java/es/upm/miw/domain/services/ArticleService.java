package es.upm.miw.domain.services;

import es.upm.miw.domain.exceptions.ConflictException;
import es.upm.miw.domain.model.Article;
import es.upm.miw.domain.model.criteria.ArticleFindCriteria;
import es.upm.miw.domain.persistence.ArticlePersistence;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ArticleService {

    private final ArticlePersistence articlePersistence;

    @Autowired
    public ArticleService(ArticlePersistence articlePersistence) {
        this.articlePersistence = articlePersistence;
    }

    public Article create(Article article) {
        this.assertNoExistsBarcode(article.getBarcode());
        article.setId(UUID.randomUUID());
        article.setRegistrationDate(LocalDateTime.now());
        return this.articlePersistence.create(article);
    }

    private void assertNoExistsBarcode(String barcode) {
        if (this.articlePersistence.existsBarcode(barcode)) {
            throw new ConflictException("The barcode already exists: " + barcode);
        }
    }

    public Article read(UUID id) {
        return this.articlePersistence.readById(id);
    }

    public Article update(String barcode, Article article) {
        Article retrieveArticle = this.articlePersistence.readByBarcode(barcode);
        if (!barcode.equals(article.getBarcode())) {
            this.assertNoExistsBarcode(article.getBarcode());
        }
        BeanUtils.copyProperties(article, retrieveArticle, "id", "registrationDate", "provider");
        return this.articlePersistence.update(barcode, retrieveArticle);
    }

    public Stream<Article> findNullSafe(ArticleFindCriteria criteria) {
        return this.articlePersistence.findNullSafe(criteria);
    }

    public Stream<Article> findByBarcodeNullSafe(String barcode) {
        return this.articlePersistence.findByBarcodeAndNotDiscontinuedNullField(barcode);
    }

    public Article readByBarcode(String barcode) {
        return this.articlePersistence.readByBarcode(barcode);
    }
}
