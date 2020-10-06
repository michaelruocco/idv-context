package uk.co.idv.method.adapter.json.otp.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;

import java.util.Collection;

public interface DeliveryMethodsMixin {

    @JsonIgnore
    Collection<DeliveryMethod> getValues();

    @JsonIgnore
    Eligibility getEligibility();

}
