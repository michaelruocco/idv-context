package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

public class InvalidPhoneNumberValueException extends RuntimeException {

    public InvalidPhoneNumberValueException(String value, Throwable cause) {
        super(value, cause);
    }

}
