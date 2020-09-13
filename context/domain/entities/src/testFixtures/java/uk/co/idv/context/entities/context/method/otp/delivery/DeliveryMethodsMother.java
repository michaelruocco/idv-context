package uk.co.idv.context.entities.context.method.otp.delivery;

public interface DeliveryMethodsMother {

    static DeliveryMethods with(DeliveryMethod... methods) {
        return new DeliveryMethods(methods);
    }

}
