package uk.co.idv.context.entities.lockout;

import uk.co.idv.context.entities.alias.IdvId;

import java.time.Instant;

public interface LockoutRequest extends ExternalLockoutRequest {

    IdvId getIdvId();

    Instant getTimestamp();

}
