package es.upm.miw.infrastructure.resources;

import es.upm.miw.domain.model.Tag;
import es.upm.miw.domain.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@PreAuthorize(Security.ADMIN_MANAGER_OPERATOR)
@RequestMapping(TagResource.TAGS)
public class TagResource {
    public static final String TAGS = "/tags";
    public static final String ID_ID = "/{id}";
    public static final String NAME = "/name";

    private final TagService tagService;

    @Autowired
    public TagResource(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(ID_ID)
    public Tag read(@PathVariable UUID id) {
        return Tag.ofTagBarcode(this.tagService.read(id));
    }

    @PreAuthorize(Security.ALL)
    @GetMapping(NAME)
    public Tag readByName(@RequestParam String  name) {
        return Tag.ofTagBarcode(this.tagService.readByName(name));
    }
}
