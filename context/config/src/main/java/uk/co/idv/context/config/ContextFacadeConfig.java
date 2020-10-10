package uk.co.idv.context.config;

import lombok.Builder;
import uk.co.idv.context.usecases.context.ContextFacade;
import uk.co.idv.context.usecases.context.identity.IdentityLoader;
import uk.co.idv.identity.usecases.eligibility.CreateEligibility;

import java.time.Clock;

@Builder
public class ContextFacadeConfig {

    @Builder.Default
    private final Clock clock = Clock.systemUTC();

    private final CreateEligibility createEligibility;
    private final ContextServiceConfig serviceConfig;

    public ContextFacade contextFacade() {
        return ContextFacade.builder()
                .identityLoader(identityLoader())
                .contextService(serviceConfig.contextService())
                .resultService(serviceConfig.resultService())
                .build();
    }

    private IdentityLoader identityLoader() {
        return IdentityLoader.builder()
                .createEligibility(createEligibility)
                .policyService(serviceConfig.policyService())
                .build();
    }

}
