package uk.co.idv.lockout.entities.policy.unlocked;

import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.policy.LockoutState;

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
