package uk.co.idv.context.adapter.json.policy.method.otp.delivery;

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
