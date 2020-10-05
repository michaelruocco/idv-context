package uk.co.idv.method.entities.otp.policy.delivery.email;

import lombok.Data;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfig;

@Data
public class EmailDeliveryMethodConfig implements DeliveryMethodConfig {

    public static final String TYPE = "email";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public RequestedData getRequestedData() {
        return RequestedData.emailAddressesOnly();
    }

}
