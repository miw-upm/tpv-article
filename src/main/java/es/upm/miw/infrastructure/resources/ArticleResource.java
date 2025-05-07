package es.upm.miw.infrastructure.resources;

import es.upm.miw.domain.model.Article;
import es.upm.miw.domain.model.criteria.ArticleFindCriteria;
import es.upm.miw.domain.services.ArticleService;
import es.upm.miw.infrastructure.resources.dtos.ArticleBarcodesDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Stream;

@RestController
@PreAuthorize(Security.ADMIN_MANAGER_OPERATOR)
@RequestMapping(ArticleResource.ARTICLES)
public class ArticleResource {
    public static final String ARTICLES = "/articles";

    public static final String ID_ID = "/{id}";
    public static final String BARCODES = "/barcodes";
    public static final String BARCODE = "/barcode";
    public static final String BARCODE_ID = "/{barcode}";

    private final ArticleService articleService;

    @Autowired
    public ArticleResource(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public Article create(@Valid @RequestBody Article article) {
        article.doDefault();
        return this.articleService.create(article);
    }

    @PreAuthorize(Security.ALL)
    @GetMapping(ID_ID)
    public Article read(@PathVariable UUID id) {
        return this.articleService.read(id);
    }

    @PreAuthorize(Security.ALL)
    @GetMapping(BARCODE + BARCODE_ID)
    public Article readByBarcode(@PathVariable String barcode) {
        return this.articleService.readByBarcode(barcode);
    }


    @GetMapping
    public Stream<Article> findNullSafe(@ModelAttribute ArticleFindCriteria criteria) {
        return this.articleService.findNullSafe(criteria)
                .map(Article::ofBarcodeDescriptionStock);
    }

    @PreAuthorize(Security.ALL)
    @GetMapping(BARCODES)
    public ArticleBarcodesDto findByBarcodeNullSafe(@RequestParam(required = false) String barcode) {
        return new ArticleBarcodesDto(
                this.articleService.findByBarcodeNullSafe(barcode)
                        .map(Article::getBarcode)
                        .toList()
        );
    }

}
