package uk.co.idv.context.adapter.json.context.method.otp.delivery.eligibility;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.method.entities.otp.policy.delivery.phone.SimSwapConfig;


public interface SimSwapEligibilityMixin {

    @JsonIgnore
    SimSwapConfig getConfig();

}
