package uk.co.idv.context.usecases.context.method.otp.simswap.async;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.idv.context.usecases.context.method.otp.simswap.sync.SyncSimSwap;
import uk.co.idv.method.entities.otp.simswap.SimSwapRequest;

@Builder
@Slf4j
public class AsyncSimSwapUpdateContextTaskFactory {

    private final ContextRepository repository;
    private final SyncSimSwap syncStrategy;

    public AsyncSimSwapUpdateContextTask build(SimSwapRequest request) {
        return AsyncSimSwapUpdateContextTask.builder()
                .repository(repository)
                .syncStrategy(syncStrategy)
                .request(request)
                .build();
    }

}
