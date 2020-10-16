package uk.co.idv.policy.adapter.json.error.handler;

import uk.co.idv.common.adapter.json.error.handler.CompositeErrorHandler;
import uk.co.idv.policy.adapter.json.error.policynotfound.PolicyNotFoundHandler;

public class PolicyErrorHandler extends CompositeErrorHandler {

    public PolicyErrorHandler() {
        super(new PolicyNotFoundHandler());
    }

}
