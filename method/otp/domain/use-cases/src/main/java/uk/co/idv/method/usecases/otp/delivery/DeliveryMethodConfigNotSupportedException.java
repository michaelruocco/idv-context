package uk.co.idv.method.usecases.otp.delivery;

public class DeliveryMethodConfigNotSupportedException extends RuntimeException {

    public DeliveryMethodConfigNotSupportedException(String type) {
        super(type);
    }

}
