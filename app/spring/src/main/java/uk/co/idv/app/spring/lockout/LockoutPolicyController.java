package uk.co.idv.app.spring.lockout;

import lombok.RequiredArgsConstructor;
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
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.context.entities.policy.DefaultPolicyRequest;
import uk.co.idv.context.entities.policy.Policies;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyService;

import java.net.URI;
import java.util.Collections;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lockout-policies")
public class LockoutPolicyController {

    private final LockoutPolicyService service;

    @GetMapping("/{id}")
    public LockoutPolicy getPolicy(@PathVariable("id") UUID id) {
        return service.load(id);
    }

    @GetMapping
    public Policies<LockoutPolicy> getPolicies(@RequestParam(required = false) String channelId,
                                               @RequestParam(required = false) String activityName,
                                               @RequestParam(required = false) String aliasType) {
        DefaultPolicyRequest request = DefaultPolicyRequest.builder()
                .channelId(channelId)
                .activityName(activityName)
                .aliasTypes(Collections.singleton(aliasType))
                .build();
        if (request.isEmpty()) {
            return service.loadAll();
        }
        return service.load(request);
    }

    @PostMapping
    public ResponseEntity<LockoutPolicy> create(@RequestBody LockoutPolicy policy) {
        service.create(policy);
        LockoutPolicy created = getPolicy(policy.getId());
        return ResponseEntity
                .created(buildGetUri(policy.getId()))
                .body(created);
    }

    @PutMapping
    public LockoutPolicy update(@RequestBody LockoutPolicy policy) {
        service.update(policy);
        return getPolicy(policy.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent()
                .build();
    }

    private static URI buildGetUri(final UUID id) {
        return linkTo(methodOn(LockoutPolicyController.class).getPolicy(id)).toUri();
    }

}
