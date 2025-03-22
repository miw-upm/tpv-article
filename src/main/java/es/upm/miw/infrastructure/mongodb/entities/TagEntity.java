package es.upm.miw.infrastructure.mongodb.entities;

import es.upm.miw.domain.model.Tag;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class TagEntity {
    @Id
    private UUID id;
    @Indexed(unique = true)
    private String name;
    private String group;
    private String description;
    @Singular
    @DBRef
    private List<ArticleEntity> articleEntities;

    public TagEntity(Tag tag) {
        BeanUtils.copyProperties(tag, this);
    }

    public Tag toTag() {
        Tag tag = new Tag();
        BeanUtils.copyProperties(this, tag);
        tag.setArticles(this.articleEntities.stream()
                .map(ArticleEntity::toArticle).toList());
        return tag;
    }

}

