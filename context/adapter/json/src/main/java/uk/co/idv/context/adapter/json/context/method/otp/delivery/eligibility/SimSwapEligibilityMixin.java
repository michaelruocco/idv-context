package uk.co.idv.context.adapter.json.context.method.otp.delivery.eligibility;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;


public interface SimSwapEligibilityMixin {

    @JsonIgnore
    SimSwapConfig getConfig();

}
