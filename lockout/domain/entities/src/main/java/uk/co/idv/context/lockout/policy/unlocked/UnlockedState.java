package uk.co.idv.context.lockout.policy.unlocked;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.lockout.attempt.VerificationAttempts;
import uk.co.idv.context.lockout.policy.LockoutState;

import java.util.UUID;

@RequiredArgsConstructor
public class UnlockedState implements LockoutState {

    private final String message;
    private final VerificationAttempts attempts;

    public UnlockedState(final VerificationAttempts attempts) {
        this("unlocked", attempts);
    }

    @Override
    public UUID getId() {
        return attempts.getId();
    }

    @Override
    public IdvId getIdvId() {
        return attempts.getIdvId();
    }

    @Override
    public VerificationAttempts getAttempts() {
        return attempts;
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
