package uk.co.idv.method.usecases.otp.simswap.sync;

import lombok.RequiredArgsConstructor;
import uk.co.idv.common.usecases.async.FutureWaiter;
import uk.co.idv.method.entities.otp.simswap.SimSwapRequest;

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
