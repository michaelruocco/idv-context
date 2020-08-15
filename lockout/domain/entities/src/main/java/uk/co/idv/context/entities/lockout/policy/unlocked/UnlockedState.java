package uk.co.idv.context.entities.lockout.policy.unlocked;

import uk.co.idv.context.entities.lockout.attempt.Attempts;
import uk.co.idv.context.entities.lockout.policy.LockoutState;

public class UnlockedState extends LockoutState {

    private final String message;

    public UnlockedState(Attempts attempts) {
        this(attempts, "unlocked");
    }

    public UnlockedState(Attempts attempts, String message) {
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
