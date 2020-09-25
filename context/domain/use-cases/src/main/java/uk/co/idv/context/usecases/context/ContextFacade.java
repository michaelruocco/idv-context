package uk.co.idv.context.usecases.context;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.DefaultCreateContextRequest;
import uk.co.idv.context.usecases.context.identity.IdentityLoader;
import uk.co.idv.context.usecases.context.lockout.LockoutStateValidator;

import java.time.Instant;
import java.util.UUID;

import static uk.co.idv.common.usecases.duration.DurationCalculator.millisBetweenNowAnd;

@Builder
@Slf4j
public class ContextFacade {

    private final IdentityLoader identityLoader;
    private final LockoutStateValidator stateValidator;
    private final ContextService contextService;

    public Context create(CreateContextRequest request) {
        Instant start = Instant.now();
        DefaultCreateContextRequest identityRequest = identityLoader.addIdentity(request);
        stateValidator.validateLockoutState(identityRequest);
        Context context = contextService.create(identityRequest);
        log.info("create context took {}ms ", millisBetweenNowAnd(start));
        return context;
    }

    public Context find(UUID id) {
        return contextService.find(id);
    }

}
