package uk.co.idv.context.entities.lockout.policy.nonlocking;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.lockout.policy.LockoutState;
import uk.co.idv.context.entities.lockout.policy.LockoutStateCalculator;
import uk.co.idv.context.entities.lockout.policy.LockoutStateRequest;

@Slf4j
@Data
public class NonLockingStateCalculator implements LockoutStateCalculator {

    public static final String TYPE = "non-locking";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public LockoutState calculate(LockoutStateRequest request) {
        log.debug("calculating non locking state with {} attempts", request.getNumberOfAttempts());
        return new NonLockingState(request.getAttempts());
    }

}
