package es.upm.miw.domain.model.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleFindCriteria {
    private String barcode;
    private String description;
    private Integer stock;
    private Boolean discontinued = true;
}
