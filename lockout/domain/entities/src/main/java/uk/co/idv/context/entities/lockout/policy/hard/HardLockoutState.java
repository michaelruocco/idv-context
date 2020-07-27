package uk.co.idv.context.entities.lockout.policy.hard;

import lombok.Builder;
import uk.co.idv.context.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.context.entities.lockout.policy.LockoutState;

public class HardLockoutState extends LockoutState {

    private final int maxNumberOfAttempts;

    @Builder
    public HardLockoutState(VerificationAttempts attempts, int maxNumberOfAttempts) {
        super(attempts);
        this.maxNumberOfAttempts = maxNumberOfAttempts;
    }

    @Override
    public boolean isLocked() {
        return getNumberOfAttemptsRemaining() <= 0;
    }

    @Override
    public String getMessage() {
        if (isLocked()) {
            return String.format("maximum number of attempts [%d] reached", maxNumberOfAttempts);
        }
        return String.format("%d attempts remaining", getNumberOfAttemptsRemaining());
    }

    public int getNumberOfAttemptsRemaining() {
        return maxNumberOfAttempts - getNumberOfAttempts();
    }

    public int getMaxNumberOfAttempts() {
        return maxNumberOfAttempts;
    }

}
