package uk.co.idv.method.adapter.json.otp.policy.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.identity.entities.identity.RequestedData;

public interface DeliveryMethodConfigMixin {

    @JsonIgnore
    RequestedData getRequestedData();

}
