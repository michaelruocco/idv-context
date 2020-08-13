package uk.co.idv.context.adapter.json.error.handler;

import uk.co.idv.context.adapter.json.error.policynotfound.PolicyNotFoundHandler;

public class PolicyErrorHandler extends CompositeErrorHandler {

    public PolicyErrorHandler() {
        super(new PolicyNotFoundHandler());
    }

}
