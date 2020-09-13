package uk.co.idv.context.usecases.context.method.otp.delivery;

import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;
import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfig;
import uk.co.idv.identity.entities.identity.Identity;

public interface DeliveryMethodConfigConverter {

    boolean supports(DeliveryMethodConfig config);

    DeliveryMethods toDeliveryMethods(Identity identity, DeliveryMethodConfig config);

}
