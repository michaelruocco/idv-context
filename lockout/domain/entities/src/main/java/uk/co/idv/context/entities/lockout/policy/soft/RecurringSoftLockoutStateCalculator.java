package uk.co.idv.context.entities.lockout.policy.soft;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.lockout.policy.LockoutState;
import uk.co.idv.context.entities.lockout.policy.LockoutStateCalculator;
import uk.co.idv.context.entities.lockout.policy.LockoutStateRequest;
import uk.co.idv.context.entities.lockout.policy.unlocked.UnlockedState;

@Slf4j
@RequiredArgsConstructor
@Data
public class RecurringSoftLockoutStateCalculator implements LockoutStateCalculator {

    public static final String TYPE = "recurring-soft-lockout";

    private final SoftLockInterval interval;

    @Getter(AccessLevel.NONE)
    private final SoftLockoutStateFactory stateFactory;

    public RecurringSoftLockoutStateCalculator(SoftLockInterval interval) {
        this(interval, new SoftLockoutStateFactory());
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public LockoutState calculate(LockoutStateRequest request) {
        log.debug("calculating recurring soft lockout state with {} attempts against interval {}",
                request.getNumberOfAttempts(),
                interval);
        if (isLocked(request.getNumberOfAttempts())) {
            return stateFactory.build(interval.getDuration(), request);
        }
        return new UnlockedState(request.getAttempts());
    }

    private boolean isLocked(int numberOfAttempts) {
        return numberOfAttempts % interval.getNumberOfAttempts() == 0;
    }

}
