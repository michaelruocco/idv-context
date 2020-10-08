package uk.co.idv.method.entities.otp.delivery.query;

import java.util.UUID;

public class DeliveryMethodNotFoundException extends RuntimeException {

    public DeliveryMethodNotFoundException(UUID id) {
        super(id.toString());
    }

}
