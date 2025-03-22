package es.upm.miw.domain.persistence;

import es.upm.miw.domain.model.Article;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface ArticlePersistence {

    Article create(Article article);

    Article readByBarcode(String barcode);

    Stream<Article> findByBarcodeAndDescriptionAndReferenceAndStockLessThanAndDiscontinuedNullSafe(
            String barcode, String description, String reference, Integer stock, Boolean discontinued);

    Article update(String barcode, Article article);

    Stream<Article> findByBarcodeAndNotDiscontinuedNullField(String barcode);

    Stream<Article> findByDiscontinuedIsFalse();

    boolean existsBarcode(String barcode);

    Stream<Article> findByProviderIsNull();
}
