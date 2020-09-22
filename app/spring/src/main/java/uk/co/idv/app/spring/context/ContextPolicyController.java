package uk.co.idv.app.spring.context;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.co.idv.app.spring.policy.CreatePolicy;
import uk.co.idv.app.spring.policy.DeletePolicy;
import uk.co.idv.app.spring.policy.LoadPolicy;
import uk.co.idv.app.spring.policy.UpdatePolicy;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.usecases.policy.ContextPolicyService;
import uk.co.idv.policy.entities.policy.Policies;

import java.net.URI;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/context-policies")
public class ContextPolicyController {

    private final LoadPolicy<ContextPolicy> load;
    private final CreatePolicy<ContextPolicy> create;
    private final UpdatePolicy<ContextPolicy> update;
    private final DeletePolicy<ContextPolicy> delete;

    public ContextPolicyController(ContextPolicyService service) {
        load = new LoadPolicy<>(service);
        create = new CreatePolicy<>(service, load);
        update = new UpdatePolicy<>(service, load);
        delete = new DeletePolicy<>(service);
    }

    @GetMapping("/{id}")
    public ContextPolicy getPolicy(@PathVariable("id") UUID id) {
        return load.load(id);
    }

    @GetMapping
    public Policies<ContextPolicy> getPolicies(@RequestParam(required = false) String channelId,
                                               @RequestParam(required = false) String activityName,
                                               @RequestParam(required = false) String aliasType) {
        return load.load(channelId, activityName, aliasType);
    }

    @PostMapping
    public ResponseEntity<ContextPolicy> create(@RequestBody ContextPolicy policy) {
        return ResponseEntity
                .created(buildGetUri(policy.getId()))
                .body(create.create(policy));
    }

    @PutMapping
    public ContextPolicy update(@RequestBody ContextPolicy policy) {
        return update.update(policy);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") UUID id) {
        return delete.delete(id);
    }

    private static URI buildGetUri(final UUID id) {
        return linkTo(methodOn(ContextPolicyController.class).getPolicy(id)).toUri();
    }

}
