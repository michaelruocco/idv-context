package uk.co.idv.context.entities.policy.method.otp.delivery.email;

import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfig;

public class EmailDeliveryMethodConfig implements DeliveryMethodConfig {

    public static final String TYPE = "email";

    @Override
    public String getType() {
        return TYPE;
    }

}
