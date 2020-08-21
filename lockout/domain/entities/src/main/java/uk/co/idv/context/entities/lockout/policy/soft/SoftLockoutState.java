package uk.co.idv.context.entities.lockout.policy.soft;

import lombok.Builder;
import uk.co.idv.context.entities.lockout.attempt.Attempts;
import uk.co.idv.context.entities.lockout.policy.LockoutState;

import java.time.Duration;
import java.time.Instant;

public class SoftLockoutState extends LockoutState {

    private final SoftLock lock;

    @Builder
    public SoftLockoutState(Attempts attempts, SoftLock lock) {
        super(attempts);
        this.lock = lock;
    }

    @Override
    public boolean isLocked() {
        return true;
    }

    @Override
    public String getMessage() {
        return String.format("soft lock began at %s and expiring at %s", lock.getStart(), lock.calculateExpiry());
    }

    public Duration getDuration() {
        return lock.getDuration();
    }

    public Instant getExpiry() {
        return lock.calculateExpiry();
    }

}
