package uk.co.idv.lockout.entities.policy;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.lockout.entities.attempt.Attempt;
import uk.co.idv.lockout.entities.attempt.Attempts;

import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class LockoutState {

    private final Attempts attempts;

    public UUID getId() {
        return attempts.getId();
    }

    public IdvId getIdvId() {
        return attempts.getIdvId();
    }

    public Attempts getAttempts() {
        return attempts;
    }

    public Collection<Attempt> attemptsCollection() {
        return attempts.toCollection();
    }

    public int getNumberOfAttempts() {
        return attempts.size();
    }

    public abstract boolean isLocked();

    public abstract String getMessage();

}
