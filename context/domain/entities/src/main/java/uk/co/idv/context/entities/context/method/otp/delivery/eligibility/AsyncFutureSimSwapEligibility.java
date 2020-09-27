package uk.co.idv.context.entities.context.method.otp.delivery.eligibility;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.entities.async.FutureHandler;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Builder
@Data
@Slf4j
public class AsyncSimSwapEligibility implements Eligibility {

    private final CompletableFuture<Eligibility> future;
    private final SimSwapConfig config;

    @Override
    public boolean isEligible() {
        return getEligibilityFromFuture().isEligible();
    }

    @Override
    public Optional<String> getReason() {
        return getEligibilityFromFuture().getReason();
    }

    public boolean isComplete() {
        return future.isDone();
    }
    
    private Eligibility getEligibilityFromFuture() {
        return FutureHandler.handle(future, buildDefaultEligibility());
    }

    private Eligibility buildDefaultEligibility() {
        return SimSwapEligibility.builder()
                .config(config)
                .status(determineDefaultFallbackStatus())
                .build();
    }

    private String determineDefaultFallbackStatus() {
        if (future.isDone()) {
            return "unknown";
        }
        return "timeout";
    }

}
