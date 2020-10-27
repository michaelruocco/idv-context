package uk.co.idv.method.usecases.otp.simswap.async;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import uk.co.idv.context.usecases.context.ContextMethodUpdater;
import uk.co.idv.method.entities.otp.simswap.SimSwapRequest;
import uk.co.idv.method.usecases.otp.delivery.OtpDeliveryMethodReplacer;
import uk.co.idv.method.usecases.otp.simswap.sync.SyncSimSwap;

import java.util.Map;

@Builder
@Data
@Slf4j
public class AsyncSimSwapUpdateContextTask implements Runnable {

    private final Map<String, String> mdc = MDC.getCopyOfContextMap();
    private final ContextMethodUpdater updater;
    private final SimSwapRequest request;
    private final SyncSimSwap syncStrategy;

    @Override
    public void run() {
        try {
            MDC.setContextMap(mdc);
            syncStrategy.waitForSimSwapsToComplete(request);
            updater.update(request.getContextId(), new OtpDeliveryMethodReplacer(request.getDeliveryMethods()));
        } finally {
            MDC.clear();
        }
    }

}
