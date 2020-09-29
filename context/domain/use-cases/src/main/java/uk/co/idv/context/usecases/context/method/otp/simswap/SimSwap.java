package uk.co.idv.context.usecases.context.method.otp.simswap;

import lombok.Builder;
import uk.co.idv.context.entities.context.method.otp.simswap.SimSwapRequest;
import uk.co.idv.context.usecases.context.method.otp.simswap.async.AsyncSimSwap;
import uk.co.idv.context.usecases.context.method.otp.simswap.sync.SyncSimSwap;

@Builder
public class SimSwap {

    private final AsyncSimSwap async;
    private final SyncSimSwap sync;

    public void waitForSimSwapsIfRequired(SimSwapRequest request) {
        if (request.allSimSwapFuturesDone()) {
            return;
        }
        if (request.hasAsyncSimSwap()) {
            async.updateContextWhenAllComplete(request);
            return;
        }
        sync.waitForSimSwapsToComplete(request);
    }

}
