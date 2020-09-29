package uk.co.idv.context.usecases.context.method.otp.simswap.sync;

import lombok.RequiredArgsConstructor;
import uk.co.idv.common.usecases.async.FutureWaiter;
import uk.co.idv.context.entities.context.method.otp.simswap.SimSwapRequest;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class SyncSimSwap {

    private final FutureWaiter futureWaiter;

    public SyncSimSwap() {
        this(new FutureWaiter());
    }

    public void waitForSimSwapsToComplete(SimSwapRequest request) {
        if (!request.allSimSwapFuturesDone()) {
            CompletableFuture<Void> futures = request.getAllSimSwapFutures();
            Duration timeout = request.getLongestSimSwapConfigTimeout();
            futureWaiter.waitFor(futures, timeout);
        }
    }

}
