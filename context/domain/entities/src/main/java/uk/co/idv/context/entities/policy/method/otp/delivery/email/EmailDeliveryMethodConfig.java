package uk.co.idv.context.entities.policy.method.otp.delivery.email;

import lombok.Data;
import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfig;
import uk.co.idv.identity.entities.identity.RequestedData;

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
