package es.upm.miw.domain.services;

import es.upm.miw.domain.exceptions.ConflictException;
import es.upm.miw.domain.model.Provider;
import es.upm.miw.domain.persistence.ProviderPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;


@Service
public class ProviderService {

    private final ProviderPersistence providerPersistence;

    @Autowired
    public ProviderService(ProviderPersistence providerPersistence) {
        this.providerPersistence = providerPersistence;
    }

    public Provider create(Provider provider) {
        this.assertNoExistsCompany(provider.getCompany());
        this.assertNoExistsNif(provider.getNif());
        return this.providerPersistence.create(provider);
    }

    private void assertNoExistsCompany(String company) {
        if (this.providerPersistence.existsCompany(company)) {
            throw new ConflictException("The nif already exists: " + company);
        }
    }

    private void assertNoExistsNif(String nif) {
        if (this.providerPersistence.existsNif(nif)) {
            throw new ConflictException("The nif already exists: " + nif);
        }
    }

    public Provider readByCompany(String company) {
        return this.providerPersistence.readByCompany(company);
    }

    public Provider update(String company, Provider provider) {
        if (!provider.getCompany().equals(company)) {
            this.assertNoExistsCompany(provider.getCompany());
        }
        if (!provider.getNif().equals(this.providerPersistence.readByCompany(company).getNif())) {
            this.assertNoExistsNif(provider.getNif());
        }
        return this.providerPersistence.update(company, provider);
    }

    public Stream<Provider> findByCompanyAndActiveIsTrueNullSave(String company) {
        return this.providerPersistence.findByCompanyAndActiveIsTrueNullSave(company);
    }

    public Stream<Provider> findByCompanyAndPhoneAndNoteNullSafe(String company, String phone, String note) {
        return this.providerPersistence.findByCompanyAndPhoneAndNoteNullSafe(company, phone, note);
    }
}
