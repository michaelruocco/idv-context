package uk.co.idv.method.usecases.otp.simswap.async;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.idv.method.entities.otp.delivery.phone.simswap.SimSwapRequestMother;
import uk.co.idv.method.entities.otp.simswap.SimSwapRequest;
import uk.co.idv.method.usecases.otp.delivery.OtpDeliveryMethodReplacer;
import uk.co.idv.method.usecases.otp.simswap.sync.SyncSimSwap;

import java.util.Optional;
import java.util.UUID;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AsyncSimSwapUpdateContextTaskTest {

    private final SimSwapRequest request = SimSwapRequestMother.build();
    private final ContextRepository repository = mock(ContextRepository.class);
    private final SyncSimSwap syncStrategy = mock(SyncSimSwap.class);

    private final AsyncSimSwapUpdateContextTask task = AsyncSimSwapUpdateContextTask.builder()
            .repository(repository)
            .request(request)
            .syncStrategy(syncStrategy)
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
        Context expectedUpdated = givenContextUpdatedTo(context);

        task.run();

        ArgumentCaptor<Context> captor = ArgumentCaptor.forClass(Context.class);
        InOrder inOrder = inOrder(syncStrategy, repository);
        inOrder.verify(syncStrategy).waitForSimSwapsToComplete(request);
        inOrder.verify(repository).load(contextId);
        inOrder.verify(repository).save(captor.capture());
        assertThat(captor.getValue()).isEqualTo(expectedUpdated);
    }

    @Test
    void shouldAllOtpDeliveryMethodReplacerWithDeliveryMethodsToUpdateContext() {
        UUID contextId = request.getContextId();
        Context context = givenContextLoaded(contextId);
        givenContextUpdatedTo(context);

        task.run();

        ArgumentCaptor<OtpDeliveryMethodReplacer> captor = ArgumentCaptor.forClass(OtpDeliveryMethodReplacer.class);
        verify(context).apply(captor.capture());
        OtpDeliveryMethodReplacer replacer = captor.getValue();
        assertThat(replacer.getDeliveryMethods()).isEqualTo(request.getDeliveryMethods());
    }

    private Context givenContextLoaded(UUID id) {
        Context context = mock(Context.class);
        given(repository.load(id)).willReturn(Optional.of(context));
        return context;
    }

    private Context givenContextUpdatedTo(Context context) {
        Context updated = mock(Context.class);
        given(context.apply(any(UnaryOperator.class))).willReturn(updated);
        return updated;
    }

}
