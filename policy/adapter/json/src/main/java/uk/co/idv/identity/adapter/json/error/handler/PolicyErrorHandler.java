package uk.co.idv.identity.adapter.json.error.handler;

import uk.co.idv.identity.adapter.json.error.policynotfound.PolicyNotFoundHandler;

public class PolicyErrorHandler extends CompositeErrorHandler {

    public PolicyErrorHandler() {
        super(new PolicyNotFoundHandler());
    }

}
