package uk.co.idv.context.adapter.json.context.method.otp.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface DeliveryMethodMixin {

    @JsonIgnore
    boolean isEligible();

}
