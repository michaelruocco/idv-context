package uk.co.idv.method.usecases.otp.simswap.async;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.usecases.context.ContextMethodUpdater;
import uk.co.idv.method.entities.otp.simswap.SimSwapRequest;
import uk.co.idv.method.usecases.otp.simswap.sync.SyncSimSwap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class AsyncSimSwapUpdateContextTaskFactoryTest {

    private final ContextMethodUpdater updater = mock(ContextMethodUpdater.class);
    private final SyncSimSwap syncStrategy = mock(SyncSimSwap.class);

    private final AsyncSimSwapUpdateContextTaskFactory factory = AsyncSimSwapUpdateContextTaskFactory.builder()
            .updater(updater)
            .syncStrategy(syncStrategy)
            .build();

    @Test
    void shouldPopulateUpdaterOnTask() {
        SimSwapRequest request = mock(SimSwapRequest.class);

        AsyncSimSwapUpdateContextTask task = factory.build(request);

        assertThat(task.getUpdater()).isEqualTo(updater);
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
