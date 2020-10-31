package uk.co.idv.context.adapter.json.error.handler;

import uk.co.idv.common.adapter.json.error.handler.CompositeErrorHandler;
import uk.co.idv.context.adapter.json.error.policynotconfigured.ContextPolicyNotConfiguredHandler;
import uk.co.idv.policy.adapter.json.error.handler.PolicyErrorHandler;

public class ContextErrorHandler extends CompositeErrorHandler {

    public ContextErrorHandler() {
        super(
                new PolicyErrorHandler(),
                new ContextPolicyNotConfiguredHandler()
        );
    }

}
