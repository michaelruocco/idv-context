package uk.co.idv.method.usecases.otp.simswap.async;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.usecases.context.ContextMethodUpdater;
import uk.co.idv.method.entities.otp.simswap.SimSwapRequest;
import uk.co.idv.method.usecases.otp.simswap.sync.SyncSimSwap;

@Builder
@Slf4j
public class AsyncSimSwapUpdateContextTaskFactory {

    private final ContextMethodUpdater updater;
    private final SyncSimSwap syncStrategy;

    public AsyncSimSwapUpdateContextTask build(SimSwapRequest request) {
        return AsyncSimSwapUpdateContextTask.builder()
                .updater(updater)
                .syncStrategy(syncStrategy)
                .request(request)
                .build();
    }

}
