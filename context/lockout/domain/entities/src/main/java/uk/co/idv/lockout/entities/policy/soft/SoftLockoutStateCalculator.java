package uk.co.idv.lockout.entities.policy.soft;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.policy.AllAttemptsFilter;
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
    private final AttemptsFilter attemptsFilter;

    @Getter(AccessLevel.NONE)
    private final SoftLockoutStateFactory stateFactory;

    public SoftLockoutStateCalculator(SoftLockIntervals intervals) {
        this(intervals, new AllAttemptsFilter());
    }

    public SoftLockoutStateCalculator(SoftLockIntervals intervals, AttemptsFilter attemptsFilter) {
        this(intervals, attemptsFilter, new SoftLockoutStateFactory());
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public LockoutState calculate(LockoutStateRequest request) {
        Attempts attempts = attemptsFilter.apply(request.getAttempts());
        log.debug("calculating soft lockout state from request with {} attempts against intervals {}",
                attempts.size(),
                intervals);
        return intervals.findInterval(attempts.size())
                .map(interval -> stateFactory.build(interval.getDuration(), request.getTimestamp(), attempts))
                .orElseGet(() -> new UnlockedState(attempts));
    }

}
