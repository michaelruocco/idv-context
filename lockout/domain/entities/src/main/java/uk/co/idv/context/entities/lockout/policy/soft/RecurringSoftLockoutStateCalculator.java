package uk.co.idv.context.entities.lockout.policy.soft;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.lockout.policy.LockoutState;
import uk.co.idv.context.entities.lockout.policy.LockoutStateCalculator;
import uk.co.idv.context.entities.lockout.policy.LockoutStateRequest;
import uk.co.idv.context.entities.lockout.policy.unlocked.UnlockedState;

@Slf4j
@RequiredArgsConstructor
public class RecurringSoftLockoutStateCalculator implements LockoutStateCalculator {

    private final SoftLockInterval interval;
    private final SoftLockoutStateFactory stateFactory;

    public RecurringSoftLockoutStateCalculator(final SoftLockInterval interval) {
        this(interval, new SoftLockoutStateFactory());
    }

    @Override
    public String getType() {
        return "recurring-soft-lockout";
    }

    @Override
    public LockoutState calculate(LockoutStateRequest request) {
        logRequest(request);
        if (isLocked(request.getNumberOfAttempts())) {
            return stateFactory.build(interval.getDuration(), request);
        }
        return new UnlockedState(request.getAttempts());
    }

    private void logRequest(LockoutStateRequest request) {
        log.debug("calculating recurring soft lockout state with {} attempts and interval {}",
                request.getNumberOfAttempts(),
                interval);
    }

    public SoftLockInterval getInterval() {
        return interval;
    }

    private boolean isLocked(int numberOfAttempts) {
        return numberOfAttempts % interval.getNumberOfAttempts() == 0;
    }

}
