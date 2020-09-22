package uk.co.idv.app.spring.policy;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.idv.policy.usecases.policy.PolicyService;

@RequiredArgsConstructor
public class CreatePolicy<P extends Policy> {

    private final PolicyService<P> service;
    private final LoadPolicy<P> loadPolicy;

    @PostMapping
    public P create(@RequestBody P policy) {
        service.create(policy);
        return loadPolicy.load(policy.getId());
    }

}
