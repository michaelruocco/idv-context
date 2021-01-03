package uk.co.idv.context.adapter.json.context.method;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;

import java.time.Duration;
import java.util.Collection;

public interface MethodsMixin {

    @JsonIgnore
    Collection<DeliveryMethod> getValues();

    @JsonIgnore
    boolean isEmpty();

    @JsonIgnore
    Duration getShortestDuration();

}
