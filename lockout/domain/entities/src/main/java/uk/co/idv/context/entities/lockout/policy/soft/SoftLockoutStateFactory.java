package uk.co.idv.context.entities.lockout.policy.soft;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.lockout.policy.LockoutState;
import uk.co.idv.context.entities.lockout.policy.LockoutStateRequest;
import uk.co.idv.context.entities.lockout.policy.unlocked.UnlockedState;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Data
public class SoftLockoutStateFactory {

    public LockoutState build(Duration duration, LockoutStateRequest request) {
        Optional<SoftLock> softLock = calculateSoftLock(duration, request);
        if (softLock.isEmpty()) {
            return new UnlockedState(request.getAttempts());
        }

        boolean isLocked = request.isBefore(softLock.get().calculateExpiry());
        if (!isLocked) {
            return new UnlockedState(request.getAttempts());
        }

        return SoftLockoutState.builder()
                .attempts(request.getAttempts())
                .lock(softLock.get())
                .build();
    }

    private static Optional<SoftLock> calculateSoftLock(Duration duration, LockoutStateRequest request) {
        return request.getMostRecentAttemptTimestamp()
                .map(timestamp -> new SoftLock(duration, timestamp));
    }

}
