package uk.co.idv.context.adapter.json.context.method.otp.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethod;

import java.util.Collection;

public interface DeliveryMethodsMixin {

    @JsonIgnore
    Collection<DeliveryMethod> getValues();

    @JsonIgnore
    Eligibility getEligibility();

}
