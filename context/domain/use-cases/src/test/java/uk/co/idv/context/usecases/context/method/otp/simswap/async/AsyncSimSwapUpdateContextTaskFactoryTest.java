package uk.co.idv.context.usecases.context.method.otp.simswap.async;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.idv.context.usecases.context.method.otp.simswap.sync.SyncSimSwap;
import uk.co.idv.method.entities.otp.simswap.SimSwapRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class AsyncSimSwapUpdateContextTaskFactoryTest {

    private final ContextRepository repository = mock(ContextRepository.class);
    private final SyncSimSwap syncStrategy = mock(SyncSimSwap.class);

    private final AsyncSimSwapUpdateContextTaskFactory factory = AsyncSimSwapUpdateContextTaskFactory.builder()
            .repository(repository)
            .syncStrategy(syncStrategy)
            .build();

    @Test
    void shouldPopulateRepositoryOnTask() {
        SimSwapRequest request = mock(SimSwapRequest.class);

        AsyncSimSwapUpdateContextTask task = factory.build(request);

        assertThat(task.getRepository()).isEqualTo(repository);
    }

    @Test
    void shouldPopulateSyncSimSwapOnTask() {
        SimSwapRequest request = mock(SimSwapRequest.class);

        AsyncSimSwapUpdateContextTask task = factory.build(request);

        assertThat(task.getSyncStrategy()).isEqualTo(syncStrategy);
    }

    @Test
    void shouldPopulateRequestOnTask() {
        SimSwapRequest request = mock(SimSwapRequest.class);

        AsyncSimSwapUpdateContextTask task = factory.build(request);

        assertThat(task.getRequest()).isEqualTo(request);
    }

}
