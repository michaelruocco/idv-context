package uk.co.idv.method.usecases.otp.delivery;

import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfig;

public interface DeliveryMethodConfigConverter {

    boolean supports(DeliveryMethodConfig config);

    DeliveryMethods toDeliveryMethods(Identity identity, DeliveryMethodConfig config);

}
