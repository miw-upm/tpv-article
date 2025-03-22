package es.upm.miw.domain.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    private String group;
    @NotBlank
    private String description;
    private List<Article> articles;

    public static Tag ofTagBarcode(Tag tag) {
        return Tag.builder()
                .name(tag.name)
                .group(tag.group)
                .description(tag.description)
                .articles(tag.articles.stream().map(Article::ofBarcode).toList())
                .build();
    }
}
