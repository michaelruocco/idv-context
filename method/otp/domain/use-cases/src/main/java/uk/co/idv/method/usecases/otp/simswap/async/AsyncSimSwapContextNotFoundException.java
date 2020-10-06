package uk.co.idv.method.usecases.otp.simswap.async;

import java.util.UUID;

public class AsyncSimSwapContextNotFoundException extends RuntimeException {

    public AsyncSimSwapContextNotFoundException(UUID id) {
        super(id.toString());
    }

}
