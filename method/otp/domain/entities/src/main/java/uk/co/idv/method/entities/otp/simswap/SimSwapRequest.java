package uk.co.idv.method.entities.otp.simswap;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;
import uk.co.idv.method.entities.otp.policy.OtpPolicy;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Builder
@Data
public class SimSwapRequest {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(5);

    private final UUID contextId;
    private final OtpPolicy policy;
    private final DeliveryMethods deliveryMethods;

    public UUID getContextId() {
        return contextId;
    }

    public boolean hasAsyncSimSwap() {
        return policy.hasAsyncSimSwap();
    }

    public CompletableFuture<Void> getAllSimSwapFutures() {
        return deliveryMethods.toSimSwapFutures().all();
    }

    public boolean allSimSwapFuturesDone() {
        return getAllSimSwapFutures().isDone();
    }

    public Duration getLongestSimSwapConfigTimeout() {
        return policy.getLongestSimSwapConfigTimeout().orElse(DEFAULT_TIMEOUT);
    }

}
