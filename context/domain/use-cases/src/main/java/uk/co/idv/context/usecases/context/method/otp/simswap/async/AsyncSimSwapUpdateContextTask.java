package uk.co.idv.context.usecases.context.method.otp.simswap.async;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.usecases.context.method.otp.delivery.ContextDeliveryMethodReplacer;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.idv.context.usecases.context.method.otp.simswap.sync.SyncSimSwap;
import uk.co.idv.method.entities.otp.simswap.SimSwapRequest;

import java.util.Optional;

@Builder
@Data
@Slf4j
public class AsyncSimSwapUpdateContextTask implements Runnable {

    private final ContextRepository repository;
    private final SimSwapRequest request;
    private final SyncSimSwap syncStrategy;
    private final ContextDeliveryMethodReplacer deliveryMethodReplacer;

    @Override
    public void run() {
        syncStrategy.waitForSimSwapsToComplete(request);
        Optional<Context> context = repository.load(request.getContextId());
        if (context.isEmpty()) {
            throw new AsyncSimSwapContextNotFoundException(request.getContextId());
        }
        updateContextDeliveryMethods(context.get());
    }

    private void updateContextDeliveryMethods(Context context) {
        Context updated = deliveryMethodReplacer.replace(context, request.getDeliveryMethods());
        log.info("updating context {} with updated delivery methods with sim swap eligibility", updated.getId());
        repository.save(updated);
    }

}
