package uk.co.idv.context.entities.lockout.policy.nonlocking;

import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.lockout.policy.AttemptsFilter;
import uk.co.idv.context.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.context.entities.lockout.policy.recordattempt.NeverRecordAttempts;

public class NonLockingPolicy extends LockoutPolicy {

    public NonLockingPolicy(PolicyKey key) {
        super(key,
                new AttemptsFilter(key),
                new NonLockingStateCalculator(),
                new NeverRecordAttempts()
        );
    }

}
