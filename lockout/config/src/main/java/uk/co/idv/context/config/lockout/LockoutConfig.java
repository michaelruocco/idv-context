package uk.co.idv.context.config.lockout;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.usecases.lockout.LockoutPolicyRepository;
import uk.co.idv.context.usecases.lockout.LockoutPolicyService;

@RequiredArgsConstructor
public class LockoutConfig {

    private final LockoutPolicyRepository repository;

    public LockoutPolicyService policyService() {
        return new LockoutPolicyService(repository);
    }

}
