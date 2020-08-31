package uk.co.idv.context.entities.policy.method.otp.delivery.email;

import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryConfig;

public class EmailDeliveryConfig implements DeliveryConfig {

    public static final String EMAIL = "email";

    @Override
    public String getName() {
        return EMAIL;
    }

}
