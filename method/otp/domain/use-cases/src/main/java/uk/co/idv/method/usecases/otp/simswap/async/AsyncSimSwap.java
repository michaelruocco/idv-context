package uk.co.idv.method.usecases.otp.simswap.async;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.method.entities.otp.simswap.SimSwapRequest;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Builder
@Slf4j
public class AsyncSimSwap {

    private final AsyncSimSwapUpdateContextTaskFactory taskFactory;
    private final ScheduledExecutorService executor;

    public void updateContextWhenAllComplete(SimSwapRequest request) {
        Runnable updateContextOnCompletion = taskFactory.build(request);
        long delay = request.getLongestSimSwapConfigTimeout().toMillis();
        log.info("scheduling async sim swap context update to run in {}ms", delay);
        executor.schedule(updateContextOnCompletion, delay, TimeUnit.MILLISECONDS);
    }

}
