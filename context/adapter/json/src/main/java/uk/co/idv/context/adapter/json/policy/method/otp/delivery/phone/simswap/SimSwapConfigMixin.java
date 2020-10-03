package uk.co.idv.context.adapter.json.policy.method.otp.delivery.phone.simswap;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Optional;

public interface SimSwapConfigMixin {

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    Optional<Long> getMinDaysSinceSwap();

}
