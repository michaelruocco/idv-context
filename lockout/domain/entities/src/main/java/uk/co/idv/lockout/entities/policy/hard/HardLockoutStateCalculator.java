package uk.co.idv.lockout.entities.policy.hard;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.entities.policy.LockoutStateCalculator;
import uk.co.idv.lockout.entities.policy.LockoutStateRequest;

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
        log.debug("calculating hard lockout state from request with {} attempts against max number of attempts {}",
                request.getNumberOfAttempts(),
                maxNumberOfAttempts);
        return new HardLockoutState(request.getAttempts(), maxNumberOfAttempts);
    }

    public int getMaxNumberOfAttempts() {
        return maxNumberOfAttempts;
    }

}
