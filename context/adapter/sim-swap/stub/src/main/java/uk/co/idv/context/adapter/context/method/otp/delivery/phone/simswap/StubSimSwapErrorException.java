package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

public class StubSimSwapErrorException extends RuntimeException {

    public StubSimSwapErrorException(String number) {
        super(number);
    }

}
