package uk.co.idv.context.config;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.usecases.context.ContextFacade;


@Builder
@Slf4j
public class ContextFacadeConfig {

    private final ContextConfig contextConfig;
    private final VerificationConfig verificationConfig;

    public ContextFacade getFacade() {
        return ContextFacade.builder()
                .identityLoader(contextConfig.identityLoader())
                .contextService(contextConfig.contextService())
                .verificationService(verificationConfig.verificationService())
                .build();
    }

}
