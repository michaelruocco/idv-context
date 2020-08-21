package uk.co.idv.context.entities.lockout.policy.soft;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.lockout.policy.LockoutState;
import uk.co.idv.context.entities.lockout.policy.LockoutStateRequest;
import uk.co.idv.context.entities.lockout.policy.unlocked.UnlockedState;

import java.time.Duration;

@Slf4j
@Data
public class SoftLockoutStateFactory {

    public LockoutState build(Duration duration, LockoutStateRequest request) {
        SoftLock softLock = calculateSoftLock(duration, request);
        boolean isLocked = request.isBefore(softLock.calculateExpiry());

        if (!isLocked) {
            return new UnlockedState(request.getAttempts());
        }

        return SoftLockoutState.builder()
                .attempts(request.getAttempts())
                .lock(softLock)
                .build();
    }

    private static SoftLock calculateSoftLock(Duration duration, LockoutStateRequest request) {
        return SoftLock.builder()
                .start(request.getMostRecentAttemptTimestamp())
                .duration(duration)
                .build();
    }

}
