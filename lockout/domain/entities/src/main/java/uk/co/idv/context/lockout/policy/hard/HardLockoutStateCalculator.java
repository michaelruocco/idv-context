package uk.co.idv.context.lockout.policy.hard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.lockout.policy.LockoutState;
import uk.co.idv.context.lockout.policy.LockoutStateCalculator;
import uk.co.idv.context.lockout.policy.LockoutStateRequest;

@Slf4j
@RequiredArgsConstructor
public class HardLockoutStateCalculator implements LockoutStateCalculator {

    private final int maxNumberOfAttempts;

    @Override
    public String getType() {
        return "hard-lockout";
    }

    @Override
    public LockoutState calculate(LockoutStateRequest request) {
        logRequest(request);
        return new HardLockoutState(request.getAttempts(), maxNumberOfAttempts);
    }

    private void logRequest(LockoutStateRequest request) {
        log.info("calculating hard locking state from request {} with max number of attempts {}",
                request,
                maxNumberOfAttempts);
    }

    public int getMaxNumberOfAttempts() {
        return maxNumberOfAttempts;
    }

}
