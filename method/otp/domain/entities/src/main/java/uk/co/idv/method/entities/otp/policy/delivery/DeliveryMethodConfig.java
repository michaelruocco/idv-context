package uk.co.idv.method.entities.otp.policy.delivery;

import uk.co.idv.method.entities.policy.RequestedDataProvider;

public interface DeliveryMethodConfig extends RequestedDataProvider {

    String getType();

}
