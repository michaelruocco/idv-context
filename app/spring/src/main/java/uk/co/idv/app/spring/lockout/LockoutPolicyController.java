package uk.co.idv.app.spring.lockout;

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
import uk.co.idv.app.plain.Application;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.policy.entities.policy.DefaultPolicyRequest;
import uk.co.idv.policy.entities.policy.Policies;

import java.net.URI;
import java.util.Collections;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/lockout-policies")
public class LockoutPolicyController {

    private final Application application;

    public LockoutPolicyController(Application application) {
        this.application = application;
    }

    @GetMapping("/{id}")
    public LockoutPolicy getPolicy(@PathVariable("id") UUID id) {
        return application.loadLockoutPolicy(id);
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
        return application.loadLockoutPolicies(request);
    }

    @PostMapping
    public ResponseEntity<LockoutPolicy> create(@RequestBody LockoutPolicy policy) {
        application.create(policy);
        return ResponseEntity
                .created(buildGetUri(policy.getId()))
                .body(application.loadLockoutPolicy(policy.getId()));
    }

    @PutMapping
    public LockoutPolicy update(@RequestBody LockoutPolicy policy) {
        application.update(policy);
        return application.loadLockoutPolicy(policy.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") UUID id) {
        application.deleteLockoutPolicy(id);
        return ResponseEntity.noContent().build();
    }

    private static URI buildGetUri(UUID id) {
        return linkTo(methodOn(LockoutPolicyController.class).getPolicy(id)).toUri();
    }

}
