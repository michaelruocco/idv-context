package uk.co.idv.context.entities.lockout.policy.soft;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.lockout.policy.LockoutState;
import uk.co.idv.context.entities.lockout.policy.LockoutStateRequest;
import uk.co.idv.context.entities.lockout.policy.unlocked.UnlockedState;

import java.time.Duration;
import java.time.Instant;

@Slf4j
public class SoftLockoutStateFactory {

    public LockoutState build(Duration duration, LockoutStateRequest request) {
        final Instant expiry = request.addToMostRecentAttemptTimestamp(duration);
        final boolean isLocked = request.isNewAttemptBefore(expiry);

        if (!isLocked) {
            return new UnlockedState(request.getAttempts());
        }

        return SoftLockoutState.builder()
                .attempts(request.getAttempts())
                .lock(new SoftLock(duration, expiry))
                .build();
    }

}
