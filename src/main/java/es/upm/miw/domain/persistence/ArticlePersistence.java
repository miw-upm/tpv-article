package es.upm.miw.domain.persistence;

import es.upm.miw.domain.model.Article;
import es.upm.miw.domain.model.criteria.ArticleFindCriteria;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface ArticlePersistence {

    Article create(Article article);

    Article readByBarcode(String barcode);

    Article readById(UUID id);

    Article update(String barcode, Article article);

    Stream<Article> findByBarcodeAndNotDiscontinuedNullField(String barcode);

    boolean existsBarcode(String barcode);

    Stream<Article> findByProviderIsNull();

    Stream<Article> findNullSafe(ArticleFindCriteria criteria);
}
