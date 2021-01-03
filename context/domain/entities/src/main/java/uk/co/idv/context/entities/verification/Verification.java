package uk.co.idv.context.entities.verification;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.method.entities.result.Result;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

//TODO test uncovered methods
@Builder(toBuilder = true)
@Data
public class Verification {

    private final UUID id;
    private final UUID contextId;
    private final Activity activity;
    private final String methodName;
    private final Methods methods;
    private final boolean protectSensitiveData;
    private final Instant created;
    private final Instant expiry;
    private final Instant completed;
    private final boolean successful;

    public Verification complete(CompleteVerificationRequest request) {
        Instant timestamp = request.forceGetTimestamp();
        validateExpiry(timestamp);
        return toBuilder()
                .successful(request.isSuccessful())
                .completed(timestamp)
                .build();
    }

    public boolean isComplete() {
        return getCompleted().isPresent();
    }

    public Optional<Instant> getCompleted() {
        return Optional.ofNullable(completed);
    }

    public boolean hasId(UUID otherId) {
        return id.equals(otherId);
    }

    public boolean hasMethodName(String otherMethodName) {
        return methodName.equals(otherMethodName);
    }

    public boolean hasExpired(Instant timestamp) {
        return timestamp.isAfter(expiry);
    }

    public Result toResult() {
        return Result.builder()
                .methodName(methodName)
                .verificationId(id)
                .successful(successful)
                .timestamp(getCompleted().orElseThrow(() -> new VerificationNotCompletedException(id)))
                .build();
    }

    private void validateExpiry(Instant timestamp) {
        if (hasExpired(timestamp)) {
            throw new VerificationExpiredException(id, expiry);
        }
    }

}
