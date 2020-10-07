package uk.co.idv.context.usecases.context;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.context.entities.result.FacadeRecordResultRequest;
import uk.co.idv.context.usecases.context.identity.IdentityLoader;
import uk.co.idv.context.usecases.context.lockout.LockoutStateValidator;
import uk.co.idv.context.usecases.context.result.ResultService;

import java.time.Instant;
import java.util.UUID;

import static uk.co.idv.common.usecases.duration.DurationCalculator.millisBetweenNowAnd;

@Builder
@Slf4j
public class ContextFacade {

    private final IdentityLoader identityLoader;
    private final LockoutStateValidator stateValidator;
    private final ContextService contextService;
    private final ResultService resultService;

    public Context create(CreateContextRequest request) {
        Instant start = Instant.now();
        ServiceCreateContextRequest identityRequest = identityLoader.addIdentity(request);
        stateValidator.validateLockoutState(identityRequest);
        Context context = contextService.create(identityRequest);
        log.info("create context took {}ms ", millisBetweenNowAnd(start));
        return context;
    }

    public Context record(FacadeRecordResultRequest request) {
        Instant start = Instant.now();
        Context context = find(request.getContextId());
        stateValidator.validateLockoutState(context);
        Context updated = resultService.record(request.toServiceRequest(context));
        log.info("record result took {}ms ", millisBetweenNowAnd(start));
        return updated;
    }

    public Context find(UUID id) {
        return contextService.find(id);
    }

}
