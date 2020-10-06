package uk.co.idv.method.adapter.json.otp.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.method.adapter.json.policy.MethodPolicyMixin;

import java.time.Duration;
import java.util.Optional;

public interface OtpPolicyMixin extends MethodPolicyMixin {

    @JsonIgnore
    boolean hasAsyncSimSwap();

    @JsonIgnore
    Optional<Duration> getLongestSimSwapConfigTimeout();

}
