package uk.co.idv.method.usecases.otp.protect.mask;

public class MethodNotOtpException extends RuntimeException {

    public MethodNotOtpException(String message) {
        super(message);
    }

}
