package uk.co.idv.context.entities.lockout.policy.unlocked;

import uk.co.idv.context.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.context.entities.lockout.policy.LockoutState;

public class UnlockedState extends LockoutState {

    private final String message;

    public UnlockedState(VerificationAttempts attempts) {
        this(attempts, "unlocked");
    }

    public UnlockedState(VerificationAttempts attempts, String message) {
        super(attempts);
        this.message = message;
    }

    @Override
    public boolean isLocked() {
        return false;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
