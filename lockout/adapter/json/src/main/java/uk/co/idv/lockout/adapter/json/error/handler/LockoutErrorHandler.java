package uk.co.idv.lockout.adapter.json.error.handler;

import uk.co.idv.common.adapter.json.error.handler.CompositeErrorHandler;
import uk.co.idv.policy.adapter.json.error.handler.PolicyErrorHandler;
import uk.co.idv.lockout.adapter.json.error.lockedout.LockedOutHandler;
import uk.co.idv.lockout.adapter.json.error.policynotconfigured.LockoutPolicyNotConfiguredHandler;

public class LockoutErrorHandler extends CompositeErrorHandler {

    public LockoutErrorHandler() {
        super(
                new PolicyErrorHandler(),
                new LockedOutHandler(),
                new LockoutPolicyNotConfiguredHandler()
        );
    }

}
