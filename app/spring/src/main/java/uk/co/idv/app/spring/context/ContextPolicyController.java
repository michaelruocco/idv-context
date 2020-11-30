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
import uk.co.idv.app.manual.Application;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.policy.entities.policy.DefaultPolicyRequest;
import uk.co.idv.policy.entities.policy.Policies;

import java.net.URI;
import java.util.Collections;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/context-policies")
public class ContextPolicyController {

    private final Application application;

    public ContextPolicyController(Application application) {
        this.application = application;
    }

    @GetMapping("/{id}")
    public ContextPolicy getPolicy(@PathVariable("id") UUID id) {
        return application.loadContextPolicy(id);
    }

    @GetMapping
    public Policies<ContextPolicy> getPolicies(@RequestParam(required = false) String channelId,
                                               @RequestParam(required = false) String activityName,
                                               @RequestParam(required = false) String aliasType) {
        DefaultPolicyRequest request = DefaultPolicyRequest.builder()
                .channelId(channelId)
                .activityName(activityName)
                .aliasTypes(Collections.singleton(aliasType))
                .build();
        return application.loadContextPolicies(request);
    }

    @PostMapping
    public ResponseEntity<ContextPolicy> create(@RequestBody ContextPolicy policy) {
        application.create(policy);
        return ResponseEntity
                .created(buildGetUri(policy.getId()))
                .body(application.loadContextPolicy(policy.getId()));
    }

    @PutMapping
    public ContextPolicy update(@RequestBody ContextPolicy policy) {
        application.update(policy);
        return application.loadContextPolicy(policy.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") UUID id) {
        application.deleteContextPolicy(id);
        return ResponseEntity.noContent().build();
    }

    private static URI buildGetUri(UUID id) {
        return linkTo(methodOn(ContextPolicyController.class).getPolicy(id)).toUri();
    }

}
