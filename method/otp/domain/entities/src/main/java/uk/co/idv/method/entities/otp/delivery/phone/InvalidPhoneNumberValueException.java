package uk.co.idv.method.entities.otp.delivery.phone;

public class InvalidPhoneNumberValueException extends RuntimeException {

    public InvalidPhoneNumberValueException(String value, Throwable cause) {
        super(value, cause);
    }

}
