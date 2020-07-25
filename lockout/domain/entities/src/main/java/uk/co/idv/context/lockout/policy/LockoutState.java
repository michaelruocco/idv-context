package uk.co.idv.context.lockout.policy;

import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.lockout.attempt.VerificationAttempts;

import java.util.UUID;

public interface LockoutState {

    UUID getId();

    IdvId getIdvId();

    boolean isLocked();

    VerificationAttempts getAttempts();

    String getMessage();

}
