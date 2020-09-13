package uk.co.idv.context.usecases.context.method.otp.delivery;

public class DeliveryMethodConfigNotSupportedException extends RuntimeException {

    public DeliveryMethodConfigNotSupportedException(String type) {
        super(type);
    }

}
