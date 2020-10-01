package uk.co.idv.context.usecases.context;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class ContextExpiredException extends RuntimeException {

    private final UUID id;
    private final Instant expiry;

    public ContextExpiredException(UUID id, Instant expiry) {
        super(toMessage(id, expiry));
        this.id = id;
        this.expiry = expiry;
    }

    private static String toMessage(UUID id, Instant expiry) {
        return String.format("context %s expired at %s", id, expiry);
    }

}
