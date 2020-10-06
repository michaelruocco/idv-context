package uk.co.idv.method.adapter.json.otp.delivery.eligibility;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.method.entities.otp.policy.delivery.phone.SimSwapConfig;


public interface SimSwapEligibilityMixin {

    @JsonIgnore
    SimSwapConfig getConfig();

}
