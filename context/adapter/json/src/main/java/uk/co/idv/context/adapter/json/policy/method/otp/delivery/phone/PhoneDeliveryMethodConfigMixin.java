package uk.co.idv.context.adapter.json.policy.method.otp.delivery.phone;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neovisionaries.i18n.CountryCode;
import uk.co.idv.context.adapter.json.policy.method.otp.delivery.DeliveryMethodConfigMixin;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;

import java.time.Duration;
import java.util.Optional;

public interface PhoneDeliveryMethodConfigMixin extends DeliveryMethodConfigMixin {

    @JsonIgnore
    CountryCode getCountry();

    @JsonIgnore
    Optional<Duration> getSimSwapTimeout();

    @JsonIgnore
    Optional<SimSwapConfig> getSimSwapConfig();

}
