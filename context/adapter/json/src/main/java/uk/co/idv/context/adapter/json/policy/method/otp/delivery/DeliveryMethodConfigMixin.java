package uk.co.idv.context.adapter.json.policy.method.otp.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.identity.entities.identity.RequestedData;

public interface DeliveryMethodConfigMixin {

    @JsonIgnore
    RequestedData getRequestedData();

}
