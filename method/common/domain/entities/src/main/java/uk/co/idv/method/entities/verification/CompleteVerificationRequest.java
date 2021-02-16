package uk.co.idv.method.entities.verification;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Builder
@Data
public class CompleteVerificationRequest {

    private final UUID id;
    private final UUID contextId;
    private final boolean successful;

    @With
    private final Instant timestamp;

    public Optional<Instant> getTimestamp() {
        return Optional.ofNullable(timestamp);
    }

    public Instant forceGetTimestamp() {
        return getTimestamp().orElseThrow(() -> new CompleteVerificationTimestampNotProvidedException(id));
    }

    public CompleteVerificationRequest withTimestampIfNotProvided(Instant timestamp) {
        return withTimestamp(getTimestamp().orElse(timestamp));
    }

}
