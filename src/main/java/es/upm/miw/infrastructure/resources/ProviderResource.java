package es.upm.miw.infrastructure.resources;

import es.upm.miw.domain.model.Provider;
import es.upm.miw.domain.services.ProviderService;
import es.upm.miw.infrastructure.resources.dtos.ProviderCompanyDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
@PreAuthorize(Security.ADMIN_MANAGER_OPERATOR)
@RequestMapping(ProviderResource.PROVIDERS)
public class ProviderResource {
    public static final String PROVIDERS = "/providers";

    public static final String COMPANY_ID = "/{company}";
    public static final String COMPANY = "/company";
    public static final String SEARCH = "/search";

    private final ProviderService providerService;

    @Autowired
    public ProviderResource(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping(produces = {"application/json"})
    public Provider create(@Valid @RequestBody Provider provider) {
        provider.doDefault();
        return this.providerService.create(provider);
    }

    @GetMapping(COMPANY_ID)
    public Provider read(@PathVariable String company) {
        return this.providerService.readByCompany(company);
    }


    @PutMapping(COMPANY_ID)
    public Provider update(@PathVariable String company, @Valid @RequestBody Provider provider) {
        provider.doDefault();
        return this.providerService.update(company, provider);
    }

    @GetMapping(COMPANY)
    public ProviderCompanyDto findByCompanyAndActiveIsTrueNullSave(@RequestParam(required = false) String company) {
        return new ProviderCompanyDto(this.providerService.findByCompanyAndActiveIsTrueNullSave(company)
                .map(Provider::getCompany)
                .toList());
    }

    @GetMapping(SEARCH)
    public Stream<Provider> findByCompanyAndPhoneAndNoteNullSafe(
            @RequestParam(required = false) String company, @RequestParam(required = false) String phone,
            @RequestParam(required = false) String note) {
        return this.providerService.findByCompanyAndPhoneAndNoteNullSafe(company, phone, note)
                .map(Provider::ofCompanyPhoneNote);
    }

}
