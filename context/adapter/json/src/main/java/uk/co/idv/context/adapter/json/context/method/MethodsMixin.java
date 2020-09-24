package uk.co.idv.context.adapter.json.context.method;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethod;

import java.util.Collection;

public interface MethodsMixin {

    @JsonIgnore
    Collection<DeliveryMethod> getValues();

    @JsonIgnore
    boolean isEligible();

    @JsonIgnore
    boolean isComplete();

}
