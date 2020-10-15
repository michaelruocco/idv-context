package uk.co.idv.policy.usecases.policy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.Policy;

@RequiredArgsConstructor
@Slf4j
public class PoliciesPopulator<P extends Policy> {

    private final PolicyPopulator<P> populator;

    public PoliciesPopulator(PolicyService<P> service) {
        this(new PolicyPopulator<>(service));
    }

    public void populate(Policies<P> policies) {
        policies.stream().forEach(populator::tryPopulate);
    }

}
