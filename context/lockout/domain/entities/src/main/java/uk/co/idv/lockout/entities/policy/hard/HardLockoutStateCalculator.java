package uk.co.idv.lockout.entities.policy.hard;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.policy.includeattempt.IncludeAllAttemptsPolicy;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.entities.policy.LockoutStateCalculator;
import uk.co.idv.lockout.entities.policy.LockoutStateRequest;
import uk.co.idv.lockout.entities.policy.includeattempt.IncludeAttemptsPolicy;

@Slf4j
@Data
public class HardLockoutStateCalculator implements LockoutStateCalculator {

    public static final String TYPE = "hard-lockout";

    private final int maxNumberOfAttempts;
    private final IncludeAttemptsPolicy includeAttemptsPolicy;

    @Override
    public String getType() {
        return TYPE;
    }

    public HardLockoutStateCalculator(int maxNumberOfAttempts) {
        this(maxNumberOfAttempts, new IncludeAllAttemptsPolicy());
    }

    @Builder
    public HardLockoutStateCalculator(int maxNumberOfAttempts, IncludeAttemptsPolicy includeAttemptsPolicy) {
        this.maxNumberOfAttempts = maxNumberOfAttempts;
        this.includeAttemptsPolicy = includeAttemptsPolicy;
    }

    @Override
    public LockoutState calculate(LockoutStateRequest request) {
        Attempts attempts = includeAttemptsPolicy.apply(request.getAttempts());
        log.debug("calculating hard lockout state from request with {} attempts against max number of attempts {}",
                attempts.size(),
                maxNumberOfAttempts);
        return new HardLockoutState(attempts, maxNumberOfAttempts);
    }

    public int getMaxNumberOfAttempts() {
        return maxNumberOfAttempts;
    }

}
