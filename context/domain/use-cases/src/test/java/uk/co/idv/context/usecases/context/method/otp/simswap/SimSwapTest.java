package uk.co.idv.context.usecases.context.method.otp.simswap;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.otp.simswap.SimSwapRequest;
import uk.co.idv.context.usecases.context.method.otp.simswap.async.AsyncSimSwap;
import uk.co.idv.context.usecases.context.method.otp.simswap.sync.SyncSimSwap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class SimSwapTest {

    private final AsyncSimSwap async = mock(AsyncSimSwap.class);
    private final SyncSimSwap sync = mock(SyncSimSwap.class);

    private final SimSwap simSwap = SimSwap.builder()
            .async(async)
            .sync(sync)
            .build();

    @Test
    void shouldDoNothingIfAllSimSwapFuturesAreDone() {
        SimSwapRequest request = mock(SimSwapRequest.class);
        given(request.allSimSwapFuturesDone()).willReturn(true);

        simSwap.waitForSimSwapsIfRequired(request);

        verify(async, never()).updateContextWhenAllComplete(any(SimSwapRequest.class));
        verify(sync, never()).waitForSimSwapsToComplete(any(SimSwapRequest.class));
    }

    @Test
    void shouldPerformUpdateContextWhenAllSimSwapsCompleteIfNotDoneAndHasAsyncSimSwap() {
        SimSwapRequest request = mock(SimSwapRequest.class);
        given(request.allSimSwapFuturesDone()).willReturn(false);
        given(request.hasAsyncSimSwap()).willReturn(true);

        simSwap.waitForSimSwapsIfRequired(request);

        verify(async).updateContextWhenAllComplete(request);
        verify(sync, never()).waitForSimSwapsToComplete(any(SimSwapRequest.class));
    }

    @Test
    void shouldPerformWaitForSimSwapsToCompleteIfNotDoneAndDoesNotHaveAsyncSimSwap() {
        SimSwapRequest request = mock(SimSwapRequest.class);
        given(request.allSimSwapFuturesDone()).willReturn(false);
        given(request.hasAsyncSimSwap()).willReturn(false);

        simSwap.waitForSimSwapsIfRequired(request);

        verify(async, never()).updateContextWhenAllComplete(any(SimSwapRequest.class));
        verify(sync).waitForSimSwapsToComplete(request);
    }

}
