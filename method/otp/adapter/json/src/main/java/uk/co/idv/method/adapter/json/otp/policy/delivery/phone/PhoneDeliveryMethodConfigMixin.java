package uk.co.idv.method.adapter.json.otp.policy.delivery.phone;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neovisionaries.i18n.CountryCode;
import uk.co.idv.method.adapter.json.otp.policy.delivery.DeliveryMethodConfigMixin;
import uk.co.idv.method.entities.otp.policy.delivery.phone.SimSwapConfig;

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
