package uk.co.idv.context.usecases.context;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.method.entities.verification.CompleteVerificationRequest;
import uk.co.idv.method.entities.verification.CompleteVerificationResult;
import uk.co.idv.method.entities.verification.CreateVerificationRequest;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.method.entities.verification.GetVerificationRequest;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.context.usecases.context.identity.IdentityLoader;
import uk.co.idv.context.usecases.context.verification.VerificationService;

import java.time.Instant;
import java.util.UUID;

import static uk.co.mruoc.duration.calculator.DurationCalculatorUtils.millisBetweenNowAnd;

@Builder
@Slf4j
public class ContextFacade {

    private final IdentityLoader identityLoader;
    private final ContextService contextService;
    private final VerificationService verificationService;

    public Context create(CreateContextRequest request) {
        Instant start = Instant.now();
        ServiceCreateContextRequest serviceRequest = identityLoader.addIdentity(request);
        Context context = contextService.create(serviceRequest);
        log.info("create context took {}ms ", millisBetweenNowAnd(start));
        return context;
    }

    public Context find(UUID id) {
        return contextService.find(id);
    }

    public Verification create(CreateVerificationRequest request) {
        return verificationService.create(request);
    }

    public Verification get(GetVerificationRequest request) {
        return verificationService.get(request);
    }

    public CompleteVerificationResult complete(CompleteVerificationRequest request) {
        return verificationService.complete(request);
    }

}
