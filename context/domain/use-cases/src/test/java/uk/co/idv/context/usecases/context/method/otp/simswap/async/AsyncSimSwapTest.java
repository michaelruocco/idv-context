package uk.co.idv.context.usecases.context.method.otp.simswap.async;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.otp.simswap.SimSwapRequest;

import java.time.Duration;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AsyncSimSwapTest {

    private final AsyncSimSwapUpdateContextTaskFactory taskFactory = mock(AsyncSimSwapUpdateContextTaskFactory.class);
    private final ScheduledExecutorService executor = mock(ScheduledExecutorService.class);

    private final AsyncSimSwap async = AsyncSimSwap.builder()
            .taskFactory(taskFactory)
            .executor(executor)
            .build();

    @Test
    void shouldScheduleTaskToUpdateContextAfterSimSwapTimeout() {
        Duration expectedDuration = Duration.ofSeconds(2);
        SimSwapRequest request = givenRequestWithSimSwapTimeout(expectedDuration);
        Runnable task = givenTaskBuiltFrom(request);

        async.updateContextWhenAllComplete(request);

        verify(executor).schedule(task, expectedDuration.toMillis(), TimeUnit.MILLISECONDS);
    }

    private SimSwapRequest givenRequestWithSimSwapTimeout(Duration timeout) {
        SimSwapRequest request = mock(SimSwapRequest.class);
        given(request.getLongestSimSwapConfigTimeout()).willReturn(timeout);
        return request;
    }

    private Runnable givenTaskBuiltFrom(SimSwapRequest request) {
        AsyncSimSwapUpdateContextTask task = mock(AsyncSimSwapUpdateContextTask.class);
        given(taskFactory.build(request)).willReturn(task);
        return task;
    }

}
