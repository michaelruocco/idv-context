package uk.co.idv.context.usecases.lockout.state;

import lombok.Getter;
import uk.co.idv.context.entities.lockout.policy.LockoutState;

@Getter
public class LockedOutException extends RuntimeException {

    private final transient LockoutState state;

    public LockedOutException(LockoutState state) {
        super(String.format("identity with idvId %s is locked", state.getIdvId()));
        this.state = state;
    }

}
