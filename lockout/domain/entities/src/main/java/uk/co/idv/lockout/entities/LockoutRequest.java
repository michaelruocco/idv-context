package uk.co.idv.lockout.entities;

import uk.co.idv.identity.entities.alias.IdvId;

import java.time.Instant;

public interface LockoutRequest extends ExternalLockoutRequest {

    IdvId getIdvId();

    Instant getTimestamp();

}
