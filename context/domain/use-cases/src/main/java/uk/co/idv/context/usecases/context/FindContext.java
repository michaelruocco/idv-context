package uk.co.idv.context.usecases.context;

import lombok.Builder;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.usecases.context.lockout.ContextLockoutService;

import java.time.Clock;
import java.util.UUID;

@Builder
public class FindContext {

    private final ContextLockoutService lockoutService;
    private final Clock clock;
    private final ContextRepository repository;
    private final MdcPopulator mdcPopulator;

    public Context find(UUID id) {
        mdcPopulator.populateContextId(id);
        Context context = repository.load(id).orElseThrow(() -> new ContextNotFoundException(id));
        if (context.hasExpired(clock.instant())) {
            throw new ContextExpiredException(id, context.getExpiry());
        }
        lockoutService.validateLockoutState(context);
        return context;
    }

}
