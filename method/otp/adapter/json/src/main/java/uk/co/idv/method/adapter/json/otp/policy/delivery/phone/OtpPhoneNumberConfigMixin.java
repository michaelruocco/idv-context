package uk.co.idv.method.adapter.json.otp.policy.delivery.phone;

import com.fasterxml.jackson.annotation.JsonInclude;
import uk.co.idv.method.entities.otp.policy.delivery.phone.SimSwapConfig;

import java.util.Optional;

public interface OtpPhoneNumberConfigMixin {

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    Optional<SimSwapConfig> getSimSwapConfig();

}
