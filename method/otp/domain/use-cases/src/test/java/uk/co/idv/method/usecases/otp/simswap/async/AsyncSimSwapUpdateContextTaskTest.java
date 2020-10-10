package uk.co.idv.method.usecases.otp.simswap.async;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.usecases.context.ContextMethodUpdater;
import uk.co.idv.method.entities.otp.delivery.phone.simswap.SimSwapRequestMother;
import uk.co.idv.method.entities.otp.simswap.SimSwapRequest;
import uk.co.idv.method.usecases.otp.delivery.OtpDeliveryMethodReplacer;
import uk.co.idv.method.usecases.otp.simswap.sync.SyncSimSwap;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

class AsyncSimSwapUpdateContextTaskTest {

    private final ContextMethodUpdater updater = mock(ContextMethodUpdater.class);
    private final SimSwapRequest request = SimSwapRequestMother.build();
    private final SyncSimSwap syncStrategy = mock(SyncSimSwap.class);

    private final AsyncSimSwapUpdateContextTask task = AsyncSimSwapUpdateContextTask.builder()
            .updater(updater)
            .request(request)
            .syncStrategy(syncStrategy)
            .build();

    @Test
    void shouldExecuteSyncStrategyToWaitForFuturesToCompleteBeforeUpdatingContext() {
        UUID contextId = request.getContextId();
        Context updated = mock(Context.class);
        given(updater.update(eq(contextId), any(OtpDeliveryMethodReplacer.class))).willReturn(updated);

        task.run();

        ArgumentCaptor<OtpDeliveryMethodReplacer> captor = ArgumentCaptor.forClass(OtpDeliveryMethodReplacer.class);
        InOrder inOrder = inOrder(syncStrategy, updater);
        inOrder.verify(syncStrategy).waitForSimSwapsToComplete(request);
        inOrder.verify(updater).update(eq(request.getContextId()), captor.capture());
        assertThat(captor.getValue().getDeliveryMethods()).isEqualTo(request.getDeliveryMethods());
    }

}
