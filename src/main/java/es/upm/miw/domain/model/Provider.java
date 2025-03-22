package es.upm.miw.domain.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Provider {
    @NotBlank
    private String company;
    @NotBlank
    private String nif;
    @NotBlank
    private String phone;
    private String address;
    private String email;
    private String note;
    private Boolean active;

    public static Provider ofCompanyPhoneNote(Provider provider) {
        return Provider.builder()
                .company(provider.getCompany())
                .phone(provider.getPhone())
                .note(provider.getNote()).build();
    }

    public static Provider ofCompany(Provider provider) {
        return Provider.builder()
                .company(provider.getCompany())
                .build();
    }

    public void doDefault() {
        if (Objects.isNull(active)) {
            this.active = true;
        }
    }

}
