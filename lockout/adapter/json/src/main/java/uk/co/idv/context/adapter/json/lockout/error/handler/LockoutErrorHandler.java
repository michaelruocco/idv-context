package uk.co.idv.context.adapter.json.lockout.error.handler;

import uk.co.idv.context.adapter.json.error.handler.CompositeErrorHandler;
import uk.co.idv.context.adapter.json.error.handler.PolicyErrorHandler;

public class LockoutErrorHandler extends CompositeErrorHandler {

    public LockoutErrorHandler() {
        super(new PolicyErrorHandler());
    }

}
