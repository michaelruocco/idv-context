package uk.co.idv.context.usecases.context.method.otp.simswap.async;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.method.otp.ContextDeliveryMethodReplacer;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;
import uk.co.idv.context.entities.context.method.otp.simswap.SimSwapRequest;
import uk.co.idv.context.entities.context.method.otp.simswap.SimSwapRequestMother;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.idv.context.usecases.context.method.otp.simswap.sync.SyncSimSwap;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

class AsyncSimSwapUpdateContextTaskTest {

    private final SimSwapRequest request = SimSwapRequestMother.build();
    private final ContextRepository repository = mock(ContextRepository.class);
    private final SyncSimSwap syncStrategy = mock(SyncSimSwap.class);
    private final ContextDeliveryMethodReplacer deliveryMethodReplacer = mock(ContextDeliveryMethodReplacer.class);

    private final AsyncSimSwapUpdateContextTask task = AsyncSimSwapUpdateContextTask.builder()
            .repository(repository)
            .request(request)
            .syncStrategy(syncStrategy)
            .deliveryMethodReplacer(deliveryMethodReplacer)
            .build();

    @Test
    void shouldThrowExceptionIfContextNotLoaded() {
        UUID contextId = request.getContextId();
        given(repository.load(request.getContextId())).willReturn(Optional.empty());

        Throwable error = catchThrowable(task::run);

        assertThat(error.getMessage()).isEqualTo(contextId.toString());
    }

    @Test
    void shouldExecuteSyncStrategyToWaitForFuturesToCompleteBeforeLoadingAndUpdatingContext() {
        UUID contextId = request.getContextId();
        Context context = givenContextLoaded(contextId);
        Context expectedUpdated = givenContextUpdatedTo(context, request.getDeliveryMethods());

        task.run();

        ArgumentCaptor<Context> captor = ArgumentCaptor.forClass(Context.class);
        InOrder inOrder = inOrder(syncStrategy, repository);
        inOrder.verify(syncStrategy).waitForSimSwapsToComplete(request);
        inOrder.verify(repository).load(contextId);
        inOrder.verify(repository).save(captor.capture());
        assertThat(captor.getValue()).isEqualTo(expectedUpdated);
    }

    private Context givenContextLoaded(UUID id) {
        Context context = mock(Context.class);
        given(repository.load(id)).willReturn(Optional.of(context));
        return context;
    }

    private Context givenContextUpdatedTo(Context context, DeliveryMethods deliveryMethods) {
        Context updated = mock(Context.class);
        given(deliveryMethodReplacer.replace(context, deliveryMethods)).willReturn(updated);
        return updated;
    }

}
