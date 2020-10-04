package uk.co.idv.context.adapter.json.context.method.otp.delivery.eligibility;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.method.entities.eligibility.Eligibility;

import java.util.concurrent.Future;

public interface AsyncSimSwapEligibilityMixin extends SimSwapEligibilityMixin {

    @JsonIgnore
    Future<Eligibility> getFuture();

}
