package uk.co.idv.context.adapter.json.lockout.error.handler;

import uk.co.idv.context.adapter.json.error.handler.CompositeErrorHandler;
import uk.co.idv.context.adapter.json.error.handler.PolicyErrorHandler;
import uk.co.idv.context.adapter.json.lockout.error.lockedout.LockedOutHandler;

public class LockoutErrorHandler extends CompositeErrorHandler {

    public LockoutErrorHandler() {
        super(new PolicyErrorHandler(), new LockedOutHandler());
    }

}
