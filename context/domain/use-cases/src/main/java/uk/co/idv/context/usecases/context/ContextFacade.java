package uk.co.idv.context.usecases.context;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.verification.Verification;
import uk.co.idv.context.entities.verification.CreateVerificationRequest;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.context.entities.result.FacadeRecordResultRequest;
import uk.co.idv.context.usecases.context.identity.IdentityLoader;
import uk.co.idv.context.usecases.context.result.ResultService;

import java.time.Instant;
import java.util.UUID;

import static uk.co.idv.common.usecases.duration.DurationCalculator.millisBetweenNowAnd;

@Builder
@Slf4j
public class ContextFacade {

    private final IdentityLoader identityLoader;
    private final ContextService contextService;
    private final ResultService resultService;
    private final VerificationService verificationService;

    public Context create(CreateContextRequest request) {
        Instant start = Instant.now();
        ServiceCreateContextRequest serviceRequest = identityLoader.addIdentity(request);
        Context context = contextService.create(serviceRequest);
        log.info("create context took {}ms ", millisBetweenNowAnd(start));
        return context;
    }

    public Verification createVerification(CreateVerificationRequest request) {
        return verificationService.create(request);
    }

    public Context record(FacadeRecordResultRequest request) {
        Instant start = Instant.now();
        Context original = find(request.getContextId());
        Context updated = resultService.record(request.toServiceRequest(original));
        log.info("record result took {}ms ", millisBetweenNowAnd(start));
        return updated;
    }

    public Context find(UUID id) {
        return contextService.find(id);
    }

}
