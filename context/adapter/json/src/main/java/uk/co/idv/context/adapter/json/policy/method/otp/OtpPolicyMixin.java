package uk.co.idv.context.adapter.json.policy.method.otp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.context.adapter.json.policy.method.MethodPolicyMixin;

import java.time.Duration;
import java.util.Optional;

public interface OtpPolicyMixin extends MethodPolicyMixin {

    @JsonIgnore
    boolean hasAsyncSimSwap();

    @JsonIgnore
    Optional<Duration> getLongestSimSwapConfigTimeout();

}
