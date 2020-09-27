package uk.co.idv.context.adapter.json.context.method.otp.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.context.entities.context.eligibility.Eligibility;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface DeliveryMethodMixin {

    @JsonIgnore
    boolean isEligible();

    @JsonIgnore
    Optional<CompletableFuture<Eligibility>> getAsyncSimSwapEligibilityFuture();

}
