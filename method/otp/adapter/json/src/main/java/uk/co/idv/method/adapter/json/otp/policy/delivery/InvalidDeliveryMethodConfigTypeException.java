package uk.co.idv.method.adapter.json.otp.policy.delivery;

public class InvalidDeliveryMethodConfigTypeException extends RuntimeException {

    public InvalidDeliveryMethodConfigTypeException(String type) {
        super(type);
    }

}
