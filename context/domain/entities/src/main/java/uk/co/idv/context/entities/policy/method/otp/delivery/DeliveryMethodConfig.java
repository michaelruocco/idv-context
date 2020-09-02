package uk.co.idv.context.entities.policy.method.otp.delivery;

import uk.co.idv.identity.entities.identity.RequestedData;

public interface DeliveryMethodConfig {

    String getType();

    RequestedData getRequestedData();

}
