package uk.co.idv.context.adapter.json.policy.method.otp.delivery.phone;

import com.fasterxml.jackson.annotation.JsonInclude;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;

import java.util.Optional;

public interface OtpPhoneNumberConfigMixin {

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    Optional<SimSwapConfig> getSimSwapConfig();

}
