package uk.co.idv.lockout.entities.policy.soft;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.entities.policy.LockoutStateCalculator;
import uk.co.idv.lockout.entities.policy.LockoutStateRequest;
import uk.co.idv.lockout.entities.policy.unlocked.UnlockedState;

@Slf4j
@RequiredArgsConstructor
@Data
public class SoftLockoutStateCalculator implements LockoutStateCalculator {

    public static final String TYPE = "soft-lockout";

    private final SoftLockIntervals intervals;

    @Getter(AccessLevel.NONE)
    private final SoftLockoutStateFactory stateFactory;

    public SoftLockoutStateCalculator(SoftLockIntervals intervals) {
        this(intervals, new SoftLockoutStateFactory());
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public LockoutState calculate(LockoutStateRequest request) {
        log.debug("calculating soft lockout state from request with {} attempts against intervals {}",
                request.getNumberOfAttempts(),
                intervals);
        return intervals.findInterval(request.getNumberOfAttempts())
                .map(interval -> stateFactory.build(interval.getDuration(), request))
                .orElseGet(() -> new UnlockedState(request.getAttempts()));
    }

}
