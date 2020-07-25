package uk.co.idv.context.lockout.policy.soft;

import lombok.Builder;
import uk.co.idv.context.lockout.attempt.VerificationAttempts;
import uk.co.idv.context.lockout.policy.LockoutState;

import java.time.Duration;
import java.time.Instant;

public class SoftLockoutState extends LockoutState {

    private final SoftLock lock;

    @Builder
    public SoftLockoutState(VerificationAttempts attempts, SoftLock lock) {
        super(attempts);
        this.lock = lock;
    }

    @Override
    public boolean isLocked() {
        return true;
    }

    @Override
    public String getMessage() {
        return String.format("soft lock expiring at %s", lock.getExpiry());
    }

    public Duration getDuration() {
        return lock.getDuration();
    }

    public Instant getExpiry() {
        return lock.getExpiry();
    }

}
