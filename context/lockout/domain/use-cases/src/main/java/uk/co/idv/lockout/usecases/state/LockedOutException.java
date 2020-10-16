package uk.co.idv.lockout.usecases.state;

import lombok.Getter;
import uk.co.idv.lockout.entities.policy.LockoutState;

@Getter
public class LockedOutException extends RuntimeException {

    private final transient LockoutState state;

    public LockedOutException(LockoutState state) {
        super(state.getIdvId().format());
        this.state = state;
    }

}
