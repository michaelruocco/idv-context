package uk.co.idv.context.entities.lockout.policy;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.lockout.attempt.VerificationAttempts;

import java.util.UUID;

@RequiredArgsConstructor
public abstract class LockoutState {

    private final VerificationAttempts attempts;

    public UUID getId() {
        return attempts.getId();
    }

    public IdvId getIdvId() {
        return attempts.getIdvId();
    }

    public VerificationAttempts getAttempts() {
        return attempts;
    }

    public int getNumberOfAttempts() {
        return attempts.size();
    }

    public abstract boolean isLocked();

    public abstract String getMessage();

}
