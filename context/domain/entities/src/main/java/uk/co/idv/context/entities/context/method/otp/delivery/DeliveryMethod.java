package uk.co.idv.context.entities.context.method.otp.delivery;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.AsyncSimSwapEligibility;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Data
@Builder
public class DeliveryMethod {

    private final UUID id;
    private final String type;
    private final String value;
    private final Eligibility eligibility;

    public Optional<CompletableFuture<Eligibility>> getAsyncSimSwapEligibilityFuture() {
        return getAsyncSimSwapEligibility().map(AsyncSimSwapEligibility::getFuture);
    }

    private Optional<AsyncSimSwapEligibility> getAsyncSimSwapEligibility() {
        if (eligibility instanceof AsyncSimSwapEligibility) {
            return Optional.of((AsyncSimSwapEligibility) eligibility);
        }
        return Optional.empty();
    }

}
