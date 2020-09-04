package uk.co.idv.context.usecases.context;

import lombok.Builder;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.DefaultCreateContextRequest;

@Builder
public class ContextFacade {

    private final IdentityLoader identityLoader;
    private final LockoutStateValidator stateValidator;
    private final ContextService contextService;

    public Context create(CreateContextRequest request) {
        DefaultCreateContextRequest identityRequest = identityLoader.addIdentity(request);
        stateValidator.validateLockoutState(identityRequest);
        return contextService.create(identityRequest);
    }

}
