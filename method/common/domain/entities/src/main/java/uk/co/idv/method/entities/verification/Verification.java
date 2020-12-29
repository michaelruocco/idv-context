package uk.co.idv.method.entities.verification;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.context.method.Methods;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Builder(toBuilder = true)
@Data
public class Verification {

    private final UUID id;
    private final UUID contextId;
    private final Activity activity;
    private final Methods methods;
    private final boolean protectSensitiveData;
    private final Instant created;
    private final Instant completed;
    private final boolean successful;

    public boolean isComplete() {
        return getCompleted().isPresent();
    }

    public Optional<Instant> getCompleted() {
        return Optional.ofNullable(completed);
    }

    public Verification complete(CompleteVerificationRequest request) {
        return toBuilder()
                .successful(request.isSuccessful())
                .completed(request.getTimestamp())
                .build();
    }

}
