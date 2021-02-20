package uk.co.idv.method.adapter.json.error.contextexpired;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.common.adapter.json.error.DefaultApiError;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Builder
@Getter
public class ContextExpiredError extends DefaultApiError {

    private static final int STATUS = 410;
    private static final String TITLE = "Context expired";

    private final UUID id;
    private final Instant expiry;

    public ContextExpiredError(UUID id, Instant expiry) {
        super(STATUS, TITLE, toMessage(id, expiry), toMeta(id, expiry));
        this.id = id;
        this.expiry = expiry;
    }

    private static String toMessage(UUID id, Instant expiry) {
        return String.format("context %s expired at %s", id, expiry);
    }

    private static Map<String, Object> toMeta(UUID id, Instant expiry) {
        return Map.of(
                "id", id.toString(),
                "expiry", expiry.toString()
        );
    }
}
