package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.context.eligibility.Eligibility;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Data
public class AsyncSimSwapResult {

    private final Duration timeout;
    private final CompletableFuture<SimSwapResult> futureResult;

    private SimSwapResult result;

    @Builder
    public AsyncSimSwapResult(Duration timeout, CompletableFuture<SimSwapResult> futureResult) {
        this.timeout = timeout;
        this.futureResult = futureResult;
    }

    public Eligibility toEligibility(Instant now) {
        if (result == null) {
            result = getResultNow();
        }
        return result.toEligibility(now);
    }

    private SimSwapResult getResultNow() {
        try {
            return futureResult.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("sim swap call did not complete successfully", e);
            return SimSwapResult.unknown();
        } catch (ExecutionException e) {
            log.error("sim swap call did not complete successfully", e);
            return SimSwapResult.unknown();
        } catch (TimeoutException e) {
            log.error("sim swap call did not complete within timeout {}", timeout, e);
            return SimSwapResult.timeout();
        }
    }

}
