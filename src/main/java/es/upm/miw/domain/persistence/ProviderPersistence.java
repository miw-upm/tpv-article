package es.upm.miw.domain.persistence;

import es.upm.miw.domain.model.Provider;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface ProviderPersistence {

    Provider create(Provider provider);

    Provider readByCompany(String company);

    boolean existsNif(String nif);

    boolean existsCompany(String company);

    Provider update(String company, Provider provider);

    Stream<Provider> findByCompanyAndActiveIsTrueNullSave(String company);

    Stream<Provider> findByCompanyAndPhoneAndNoteNullSafe(String company, String phone, String note);
}
