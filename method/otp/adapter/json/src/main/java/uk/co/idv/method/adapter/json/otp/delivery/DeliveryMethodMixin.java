package uk.co.idv.method.adapter.json.otp.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import uk.co.idv.method.entities.eligibility.Eligibility;

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

    @JsonIgnore
    boolean isEligibilityCompleteAndEligible();

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    Optional<Instant> getLastUpdated();

}
