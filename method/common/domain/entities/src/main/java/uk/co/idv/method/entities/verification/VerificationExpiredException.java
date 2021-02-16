package uk.co.idv.method.entities.verification;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class VerificationExpiredException extends RuntimeException {

    private final UUID id;
    private final Instant expiry;

    public VerificationExpiredException(UUID id, Instant expiry) {
        super(toMessage(id, expiry));
        this.id = id;
        this.expiry = expiry;
    }

    private static String toMessage(UUID id, Instant expiry) {
        return String.format("verification %s expired at %s", id, expiry);
    }

}
