package es.upm.miw.infrastructure.mongodb.repositories;

import es.upm.miw.infrastructure.mongodb.entities.ArticleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArticleRepository extends MongoRepository<ArticleEntity, UUID> {
    Optional<ArticleEntity> findByBarcode(String barcode);

    boolean existsByBarcode(String barcode);

    List<ArticleEntity> findByProviderEntityId(String providerEntityId);

    List<ArticleEntity> findByDiscontinuedIsFalse();

    List<ArticleEntity> findByProviderEntityIsNull();

    @Query("{$and:[" // allow NULL: all elements
            + "?#{ [0] == null ? {_id : {$ne:null}} : { barcode : {$regex:[0], $options: 'i'} } },"
            + "?#{ [1] == null ? {_id : {$ne:null}} : { description : {$regex:[1], $options: 'i'} } },"
            + "?#{ [2] == null ? {_id : {$ne:null}} : { reference : {$regex:[2], $options: 'i'} } },"
            + "?#{ [3] == null ? {_id : {$ne:null}} : { stock : {$lt:[3]} } },"
            + "?#{ [4] == null ? {_id : {$ne:null}} : { discontinued : [4] } }"
            + "] }")
    List<ArticleEntity> findByBarcodeAndDescriptionAndReferenceAndStockLessThanAndDiscontinuedNullSafe(
            String barcode, String description, String reference, Integer stock, Boolean discontinued);

    @Query("{$and:[" // allow NULL in barcode
            + "?#{ [0] == null ? {_id : {$ne:null}} : { barcode : {$regex:[0], $options: 'i'} } },"
            + "{discontinued : false}"
            + "] }")
    List<ArticleEntity> findByBarcodeLikeAndNotDiscontinuedNullSafe(String barcode);
}
