package uk.co.idv.policy.usecases.policy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.idv.policy.entities.policy.key.PolicyKey;

@RequiredArgsConstructor
@Slf4j
public class PolicyPopulator<P extends Policy> {

    private final PolicyService<P> service;

    public boolean tryPopulate(P policy) {
        PolicyKey key = policy.getKey();
        if (exists(key)) {
            log.info("policy already exists for key {}", key);
            return false;
        }
        populate(policy);
        return true;
    }

    private boolean exists(PolicyKey key) {
        return !service.load(key).isEmpty();
    }

    private void populate(P policy) {
        log.info("populating policy for key {}", policy.getKey());
        service.create(policy);
    }

}
