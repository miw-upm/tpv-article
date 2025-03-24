package es.upm.miw.domain.model.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderFindCriteria {
    private String company;
    private String phone;
    private String note;
    private Boolean active = true;
}
