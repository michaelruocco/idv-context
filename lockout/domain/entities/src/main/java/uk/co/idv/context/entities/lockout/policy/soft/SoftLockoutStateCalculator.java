package uk.co.idv.context.entities.lockout.policy.soft;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.lockout.policy.LockoutState;
import uk.co.idv.context.entities.lockout.policy.LockoutStateCalculator;
import uk.co.idv.context.entities.lockout.policy.LockoutStateRequest;
import uk.co.idv.context.entities.lockout.policy.unlocked.UnlockedState;

@Slf4j
@RequiredArgsConstructor
public class SoftLockoutStateCalculator implements LockoutStateCalculator {

    private final SoftLockIntervals intervals;
    private final SoftLockoutStateFactory stateFactory;

    public SoftLockoutStateCalculator(SoftLockIntervals intervals) {
        this(intervals, new SoftLockoutStateFactory());
    }

    @Override
    public String getType() {
        return "soft-lockout";
    }

    @Override
    public LockoutState calculate(LockoutStateRequest request) {
        logRequest(request);
        return intervals.findInterval(request.getNumberOfAttempts())
                .map(interval -> stateFactory.build(interval.getDuration(), request))
                .orElseGet(() -> new UnlockedState(request.getAttempts()));
    }

    private void logRequest(LockoutStateRequest request) {
        log.debug("calculating soft lockout state from request with {} attempts and intervals {}",
                request.getNumberOfAttempts(),
                intervals);
    }

    public SoftLockIntervals getIntervals() {
        return intervals;
    }

}
