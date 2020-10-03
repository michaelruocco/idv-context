package uk.co.idv.context.adapter.json.context.method.otp.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import uk.co.idv.context.entities.context.eligibility.Eligibility;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface DeliveryMethodMixin {

    @JsonIgnore
    boolean isEligible();

    @JsonIgnore
    Optional<CompletableFuture<Eligibility>> getAsyncSimSwapEligibilityFuture();

    @JsonIgnore
    boolean isEligibilityComplete();

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    Optional<Instant> getLastUpdated();

}
