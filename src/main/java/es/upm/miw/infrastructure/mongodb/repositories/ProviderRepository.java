package es.upm.miw.infrastructure.mongodb.repositories;

import es.upm.miw.infrastructure.mongodb.entities.ProviderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProviderRepository extends MongoRepository<ProviderEntity, UUID> {
    Optional<ProviderEntity> findByCompany(String company);

    boolean existsByCompany(String company);

    boolean existsByNif(String nif);

    @Query("{$and:[" // allow NULL in barcode
            + "?#{ [0] == null ? {_id : {$ne:null}} : { company : {$regex:[0], $options: 'i'} } },"
            + "{active : true}"
            + "] }")
    List<ProviderEntity> findByCompanyAndActiveIsTrueNullSave(String company);

    @Query("{$and:[" // allow NULL: all elements
            + "?#{ [0] == null ? {_id : {$ne:null}} : { company : {$regex:[0], $options: 'i'} } },"
            + "?#{ [1] == null ? {_id : {$ne:null}} : { phone : {$regex:[1], $options: 'i'} } },"
            + "?#{ [2] == null ? {_id : {$ne:null}} : { note :{$regex:[2], $options: 'i'} }  }"
            + "] }")
    List<ProviderEntity> findByCompanyAndPhoneAndNoteNullSafe(
            String company, String phone, String note);
}
