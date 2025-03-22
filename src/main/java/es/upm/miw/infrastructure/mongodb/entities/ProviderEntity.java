package es.upm.miw.infrastructure.mongodb.entities;

import es.upm.miw.domain.model.Provider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class ProviderEntity {
    @Id
    private UUID id;
    @Indexed(unique = true)
    private String company;
    @Indexed(unique = true)
    private String nif;
    private String phone;
    private String address;
    private String email;
    private String note;
    private Boolean active;

    public ProviderEntity(Provider provider) {
        BeanUtils.copyProperties(provider, this);
        this.id = UUID.randomUUID();
    }

    public Provider toProvider() {
        Provider provider = new Provider();
        BeanUtils.copyProperties(this, provider);
        return provider;
    }
}
