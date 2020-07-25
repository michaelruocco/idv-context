package uk.co.idv.context.lockout.policy.hard;

import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.lockout.policy.AttemptsFilter;
import uk.co.idv.context.lockout.policy.LockoutPolicy;
import uk.co.idv.context.lockout.policy.recordattempt.AlwaysRecordAttempts;

public class HardLockoutPolicy extends LockoutPolicy {

    public HardLockoutPolicy(PolicyKey key, int maxNumberOfAttempts) {
        super(key,
                new AttemptsFilter(key),
                new HardLockoutStateCalculator(maxNumberOfAttempts),
                new AlwaysRecordAttempts()
        );
    }

}
