package uk.co.idv.context.config.lockout;

import lombok.Builder;
import uk.co.idv.context.adapter.json.lockout.error.handler.LockoutErrorHandler;
import uk.co.idv.context.usecases.lockout.LockoutPolicyRepository;
import uk.co.idv.context.usecases.lockout.LockoutPolicyService;

@Builder
public class LockoutConfig {

    private final LockoutPolicyRepository repository;

    public LockoutPolicyService policyService() {
        return new LockoutPolicyService(repository);
    }

    public LockoutErrorHandler errorHandler() {
        return new LockoutErrorHandler();
    }

}
