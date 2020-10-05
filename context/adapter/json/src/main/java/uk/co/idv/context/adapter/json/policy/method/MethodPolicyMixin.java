package uk.co.idv.context.adapter.json.policy.method.otp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.identity.entities.identity.RequestedData;

import java.time.Duration;
import java.util.Optional;

public interface OtpPolicyMixin {

    @JsonIgnore
    RequestedData getRequestedData();

    @JsonIgnore
    boolean hasAsyncSimSwap();

    @JsonIgnore
    Optional<Duration> getLongestSimSwapConfigTimeout();

}
