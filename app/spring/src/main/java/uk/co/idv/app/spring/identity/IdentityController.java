package uk.co.idv.app.spring.identity;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.usecases.identity.IdentityService;

import java.net.URI;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/identities")
public class IdentityController {

    private final IdentityService facade;

    @GetMapping
    public Identity getIdentity(@RequestParam String aliasType,
                                @RequestParam String aliasValue) {
        return facade.find(aliasType, aliasValue);
    }

    @GetMapping("/{idvId}")
    public Identity getIdentity(@PathVariable("idvId") UUID id) {
        return facade.find(id);
    }

    @PostMapping
    public ResponseEntity<Identity> upsertIdentity(@RequestBody Identity identity) {
        Identity updated = facade.update(identity);
        return ResponseEntity
                .created(buildGetUri(updated.getIdvIdValue()))
                .body(updated);
    }

    private static URI buildGetUri(UUID id) {
        return linkTo(methodOn(IdentityController.class).getIdentity(id)).toUri();
    }

}
