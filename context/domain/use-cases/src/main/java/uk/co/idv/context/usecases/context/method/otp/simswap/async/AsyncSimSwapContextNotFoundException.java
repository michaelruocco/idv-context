package uk.co.idv.context.usecases.context.method.otp.simswap.async;

import java.util.UUID;

public class AsyncSimSwapContextNotFoundException extends RuntimeException {

    public AsyncSimSwapContextNotFoundException(UUID id) {
        super(id.toString());
    }

}
