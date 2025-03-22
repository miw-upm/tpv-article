package es.upm.miw.infrastructure.mongodb.persistence;

import es.upm.miw.domain.exceptions.NotFoundException;
import es.upm.miw.domain.model.Provider;
import es.upm.miw.domain.persistence.ProviderPersistence;
import es.upm.miw.infrastructure.mongodb.entities.ProviderEntity;
import es.upm.miw.infrastructure.mongodb.repositories.ProviderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public class ProviderPersistenceMongodb implements ProviderPersistence {

    private final ProviderRepository providerRepository;

    @Autowired
    public ProviderPersistenceMongodb(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    @Override
    public Provider create(Provider provider) {
        return this.providerRepository.save(new ProviderEntity(provider))
                .toProvider();
    }

    @Override
    public Provider readByCompany(String company) {
        return this.providerRepository.findByCompany(company)
                .orElseThrow(() -> new NotFoundException("Non existent company: " + company))
                .toProvider();
    }

    @Override
    public boolean existsNif(String nif) {
        return this.providerRepository.existsByNif(nif);
    }

    @Override
    public boolean existsCompany(String company) {
        return this.providerRepository.existsByCompany(company);
    }

    @Override
    public Provider update(String company, Provider provider) {
        ProviderEntity providerEntity = this.providerRepository.findByCompany(company)
                .orElseThrow(() -> new NotFoundException("Non existent company: " + company));
        BeanUtils.copyProperties(provider, providerEntity);
        return this.providerRepository.save(providerEntity).toProvider();
    }

    @Override
    public Stream<Provider> findByCompanyAndActiveIsTrueNullSave(String company) {
        return this.providerRepository.findByCompanyAndActiveIsTrueNullSave(company).stream()
                .map(ProviderEntity::toProvider);
    }

    @Override
    public Stream<Provider> findByCompanyAndPhoneAndNoteNullSafe(String company, String phone, String note) {
        return this.providerRepository.findByCompanyAndPhoneAndNoteNullSafe(company, phone, note).stream()
                .map(ProviderEntity::toProvider);
    }

}
