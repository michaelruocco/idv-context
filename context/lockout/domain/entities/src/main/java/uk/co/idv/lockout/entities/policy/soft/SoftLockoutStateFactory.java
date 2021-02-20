package uk.co.idv.lockout.entities.policy.soft;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.entities.policy.unlocked.UnlockedState;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Slf4j
@Data
public class SoftLockoutStateFactory {

    public LockoutState build(Duration duration, Instant requestTime, Attempts attempts) {
        Optional<SoftLock> softLock = calculateSoftLock(duration, attempts);
        if (softLock.isEmpty() || !isLocked(requestTime, softLock.get())) {
            return new UnlockedState(attempts);
        }
        return SoftLockoutState.builder()
                .attempts(attempts)
                .lock(softLock.get())
                .build();
    }

    private static Optional<SoftLock> calculateSoftLock(Duration duration, Attempts attempts) {
        return attempts.getMostRecentTimestamp()
                .map(timestamp -> new SoftLock(duration, timestamp));
    }

    private static boolean isLocked(Instant requestTime, SoftLock softLock) {
        Instant expiry = softLock.calculateExpiry();
        boolean isLocked = requestTime.isBefore(expiry);
        log.info("request at {} soft lock expiry at {} is locked {}", requestTime, expiry, isLocked);
        return isLocked;
    }

}
