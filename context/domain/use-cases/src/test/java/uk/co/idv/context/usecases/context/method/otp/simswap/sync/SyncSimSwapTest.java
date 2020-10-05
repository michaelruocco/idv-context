package uk.co.idv.context.usecases.context.method.otp.simswap.sync;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.usecases.async.FutureWaiter;
import uk.co.idv.method.entities.otp.simswap.SimSwapRequest;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class SyncSimSwapTest {

    private final FutureWaiter futureWaiter = mock(FutureWaiter.class);

    private final SyncSimSwap sync = new SyncSimSwap(futureWaiter);

    @Test
    void shouldNotWaitIfAllSimSwapFuturesAreAllDone() {
        SimSwapRequest request = mock(SimSwapRequest.class);
        given(request.allSimSwapFuturesDone()).willReturn(true);

        sync.waitForSimSwapsToComplete(request);

        verify(futureWaiter, never()).waitFor(any(CompletableFuture.class), any(Duration.class));
    }

    @Test
    void shouldWaitIfAnySimSwapFuturesAreNotDone() {
        Duration expectedTimeout = Duration.ofSeconds(1);
        SimSwapRequest request = mock(SimSwapRequest.class);
        CompletableFuture<Void> expectedFuture = mock(CompletableFuture.class);
        given(request.allSimSwapFuturesDone()).willReturn(false);
        given(request.getLongestSimSwapConfigTimeout()).willReturn(expectedTimeout);
        given(request.getAllSimSwapFutures()).willReturn(expectedFuture);

        sync.waitForSimSwapsToComplete(request);

        verify(futureWaiter).waitFor(expectedFuture, expectedTimeout);
    }

}
