package uk.co.idv.method.adapter.json.otp.policy.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.identity.entities.identity.RequestedData;

import java.time.Duration;
import java.util.Optional;

public interface DeliveryMethodConfigsMixin {

    @JsonIgnore
    RequestedData getRequestedData();

    @JsonIgnore
    Optional<Duration> getLongestSimSwapConfigTimeout();

}
