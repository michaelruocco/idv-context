package uk.co.idv.context.config;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.context.usecases.context.ContextFacade;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.idv.context.usecases.context.verification.CompleteVerification;
import uk.co.idv.context.usecases.context.verification.CreateVerification;
import uk.co.idv.context.usecases.context.verification.GetVerification;
import uk.co.idv.context.usecases.context.verification.VerificationService;

import java.time.Clock;

@Builder
@Slf4j
public class VerificationConfig {

    private final Clock clock;
    private final IdGenerator idGenerator;
    private final ContextConfig contextConfig;
    private final ContextRepository contextRepository;

    public ContextFacade getFacade() {
        return ContextFacade.builder()
                .identityLoader(contextConfig.identityLoader())
                .contextService(contextConfig.contextService())
                .verificationService(verificationService())
                .build();
    }

    public VerificationService verificationService() {
        return VerificationService.builder()
                .createVerification(createVerification())
                .completeVerification(completeVerification())
                .getVerification(getVerification())
                .build();
    }

    private CreateVerification createVerification() {
        return CreateVerification.builder()
                .findContext(contextConfig.findContext())
                .repository(contextRepository)
                .idGenerator(idGenerator)
                .clock(clock)
                .build();
    }

    private CompleteVerification completeVerification() {
        return CompleteVerification.builder()
                .findContext(contextConfig.findContext())
                .lockoutService(contextConfig.lockoutService())
                .repository(contextRepository)
                .clock(clock)
                .build();
    }

    private GetVerification getVerification() {
        return GetVerification.builder()
                .findContext(contextConfig.findContext())
                .repository(contextRepository)
                .build();
    }

}
