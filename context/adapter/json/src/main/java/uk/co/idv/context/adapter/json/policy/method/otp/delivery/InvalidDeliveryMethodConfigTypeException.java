package uk.co.idv.context.adapter.json.policy.method.otp.delivery;

public class InvalidDeliveryMethodConfigTypeException extends RuntimeException {

    public InvalidDeliveryMethodConfigTypeException(String type) {
        super(type);
    }

}
