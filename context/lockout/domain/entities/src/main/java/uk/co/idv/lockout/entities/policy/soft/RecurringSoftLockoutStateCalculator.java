package uk.co.idv.lockout.entities.policy.soft;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.policy.includeattempt.IncludeAllAttemptsPolicy;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.entities.policy.LockoutStateCalculator;
import uk.co.idv.lockout.entities.policy.LockoutStateRequest;
import uk.co.idv.lockout.entities.policy.includeattempt.IncludeAttemptsPolicy;
import uk.co.idv.lockout.entities.policy.unlocked.UnlockedState;

@Slf4j
@RequiredArgsConstructor
@Data
public class RecurringSoftLockoutStateCalculator implements LockoutStateCalculator {

    public static final String TYPE = "recurring-soft-lockout";

    private final SoftLockInterval interval;
    private final IncludeAttemptsPolicy includeAttemptsPolicy;

    @Getter(AccessLevel.NONE)
    private final SoftLockoutStateFactory stateFactory;

    public RecurringSoftLockoutStateCalculator(SoftLockInterval interval) {
        this(interval, new IncludeAllAttemptsPolicy());
    }

    @Builder
    public RecurringSoftLockoutStateCalculator(SoftLockInterval interval, IncludeAttemptsPolicy includeAttemptsPolicy) {
        this(interval, includeAttemptsPolicy, new SoftLockoutStateFactory());
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public LockoutState calculate(LockoutStateRequest request) {
        Attempts attempts = includeAttemptsPolicy.apply(request.getAttempts());
        log.debug("calculating recurring soft lockout state with {} attempts against interval {}",
                attempts.size(),
                interval);
        if (isLocked(attempts.size())) {
            return stateFactory.build(interval.getDuration(), request.getTimestamp(), attempts);
        }
        return new UnlockedState(attempts);
    }

    private boolean isLocked(int numberOfAttempts) {
        return numberOfAttempts % interval.getNumberOfAttempts() == 0;
    }

}
