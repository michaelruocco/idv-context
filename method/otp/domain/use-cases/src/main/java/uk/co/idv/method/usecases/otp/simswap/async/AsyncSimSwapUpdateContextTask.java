package uk.co.idv.method.usecases.otp.simswap.async;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.usecases.context.ContextMethodUpdater;
import uk.co.idv.method.entities.otp.simswap.SimSwapRequest;
import uk.co.idv.method.usecases.otp.delivery.OtpDeliveryMethodReplacer;
import uk.co.idv.method.usecases.otp.simswap.sync.SyncSimSwap;

@Builder
@Data
@Slf4j
public class AsyncSimSwapUpdateContextTask implements Runnable {

    private final ContextMethodUpdater updater;
    private final SimSwapRequest request;
    private final SyncSimSwap syncStrategy;

    @Override
    public void run() {
        syncStrategy.waitForSimSwapsToComplete(request);
        updater.update(request.getContextId(), new OtpDeliveryMethodReplacer(request.getDeliveryMethods()));
    }

}
