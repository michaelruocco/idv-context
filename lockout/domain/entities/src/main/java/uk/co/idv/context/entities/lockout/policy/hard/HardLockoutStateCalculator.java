package uk.co.idv.context.entities.lockout.policy.hard;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.lockout.policy.LockoutState;
import uk.co.idv.context.entities.lockout.policy.LockoutStateCalculator;
import uk.co.idv.context.entities.lockout.policy.LockoutStateRequest;

@Slf4j
@RequiredArgsConstructor
@Data
public class HardLockoutStateCalculator implements LockoutStateCalculator {

    public static final String TYPE = "hard-lockout";

    private final int maxNumberOfAttempts;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public LockoutState calculate(LockoutStateRequest request) {
        logRequest(request);
        return new HardLockoutState(request.getAttempts(), maxNumberOfAttempts);
    }

    private void logRequest(LockoutStateRequest request) {
        log.debug("calculating hard lockout state from request with {} attempts and max number of attempts {}",
                request.getNumberOfAttempts(),
                maxNumberOfAttempts);
    }

    public int getMaxNumberOfAttempts() {
        return maxNumberOfAttempts;
    }

}
