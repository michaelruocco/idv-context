package uk.co.idv.method.entities.otp.delivery;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import uk.co.idv.identity.entities.Updatable;
import uk.co.idv.method.entities.eligibility.AsyncEligibility;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.otp.simswap.eligibility.AsyncFutureSimSwapEligibility;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Data
@Builder
public class DeliveryMethod implements Updatable<DeliveryMethod> {

    private final UUID id;
    private final String type;

    @With
    private final String value;

    private final Instant lastUpdated;
    private final Eligibility eligibility;

    public Optional<CompletableFuture<Eligibility>> getAsyncSimSwapEligibilityFuture() {
        return getAsyncSimSwapEligibility().map(AsyncFutureSimSwapEligibility::getFuture);
    }

    public boolean isEligible() {
        return eligibility.isEligible();
    }

    public Optional<Instant> getLastUpdated() {
        return Optional.ofNullable(lastUpdated);
    }

    public boolean isEligibilityComplete() {
        if (eligibility instanceof AsyncEligibility) {
            AsyncEligibility asyncEligibility = (AsyncEligibility) eligibility;
            return asyncEligibility.isComplete();
        }
        return true;
    }

    private Optional<AsyncFutureSimSwapEligibility> getAsyncSimSwapEligibility() {
        if (eligibility instanceof AsyncFutureSimSwapEligibility) {
            return Optional.of((AsyncFutureSimSwapEligibility) eligibility);
        }
        return Optional.empty();
    }

}
