package es.upm.miw.infrastructure.mongodb.entities;

import es.upm.miw.domain.model.Article;
import es.upm.miw.domain.model.Tax;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class ArticleEntity {
    @Id
    private UUID id;
    @Indexed(unique = true)
    private String barcode;
    private String description;
    private BigDecimal retailPrice;
    private Integer stock;
    private Tax tax;
    private LocalDateTime registrationDate;
    private Boolean discontinued;
    @DBRef
    private ProviderEntity providerEntity;

    public ArticleEntity(Article article) {
        BeanUtils.copyProperties(article, this);
    }

    public Article toArticle() {
        Article article = new Article();
        BeanUtils.copyProperties(this, article);
        if (Objects.nonNull(this.getProviderEntity())) {
            article.setProvider(this.getProviderEntity().toProvider());
        }
        return article;
    }

}
