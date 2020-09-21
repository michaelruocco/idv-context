package uk.co.idv.context.config;

import lombok.Builder;
import uk.co.idv.context.usecases.context.ContextFacade;
import uk.co.idv.context.usecases.context.IdentityLoader;
import uk.co.idv.context.usecases.context.LockoutStateValidator;
import uk.co.idv.identity.usecases.eligibility.CreateEligibility;
import uk.co.idv.lockout.usecases.LockoutService;

@Builder
public class ContextFacadeConfig {

    private final CreateEligibility createEligibility;
    private final LockoutService lockoutService;
    private final ContextServiceConfig serviceConfig;

    public ContextFacade contextFacade() {
        return ContextFacade.builder()
                .identityLoader(identityLoader())
                .stateValidator(stateValidator())
                .contextService(serviceConfig.contextService())
                .build();
    }

    private IdentityLoader identityLoader() {
        return IdentityLoader.builder()
                .createEligibility(createEligibility)
                .policyService(serviceConfig.policyService())
                .build();
    }

    private LockoutStateValidator stateValidator() {
        return LockoutStateValidator.builder()
                .lockoutService(lockoutService)
                .build();
    }

}
