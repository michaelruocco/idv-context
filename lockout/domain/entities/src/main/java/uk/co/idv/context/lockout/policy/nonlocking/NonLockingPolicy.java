package uk.co.idv.context.lockout.policy.nonlocking;

import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.lockout.policy.AttemptsFilter;
import uk.co.idv.context.lockout.policy.LockoutPolicy;
import uk.co.idv.context.lockout.policy.recordattempt.NeverRecordAttempts;

public class NonLockingPolicy extends LockoutPolicy {

    //TODO tests for this class
    public NonLockingPolicy(PolicyKey key) {
        super(key, new AttemptsFilter(key), new NonLockingStateCalculator(), new NeverRecordAttempts());
    }

}
