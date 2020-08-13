package uk.co.idv.context.adapter.json.lockout.error.handler;

import uk.co.idv.context.adapter.json.error.handler.CompositeErrorHandler;
import uk.co.idv.context.adapter.json.error.handler.PolicyErrorHandler;
import uk.co.idv.context.adapter.json.error.internalserver.InternalServerHandler;

public class LockoutErrorHandler extends CompositeErrorHandler {

    public LockoutErrorHandler() {
        super(
                new InternalServerHandler(),
                new PolicyErrorHandler()
        );
    }

}
