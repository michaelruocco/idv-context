package uk.co.idv.lockout.entities.policy.soft;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.entities.policy.LockoutStateRequest;
import uk.co.idv.lockout.entities.policy.unlocked.UnlockedState;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Slf4j
@Data
public class SoftLockoutStateFactory {

    public LockoutState build(Duration duration, LockoutStateRequest request) {
        Optional<SoftLock> softLock = calculateSoftLock(duration, request);
        if (softLock.isEmpty() || !isLocked(request, softLock.get())) {
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

    private static boolean isLocked(LockoutStateRequest request, SoftLock softLock) {
        Instant expiry = softLock.calculateExpiry();
        boolean isLocked = request.isBefore(expiry);
        log.info("request at {} soft lock expiry at {} is locked {}", request.getTimestamp(), expiry, isLocked);
        return isLocked;
    }

}
