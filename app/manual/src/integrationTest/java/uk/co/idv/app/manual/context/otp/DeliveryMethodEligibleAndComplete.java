package uk.co.idv.app.manual.context.otp;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.app.manual.Application;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.method.entities.otp.delivery.query.DeliveryMethodEligible;

import java.util.UUID;
import java.util.concurrent.Callable;

@Builder
public class DeliveryMethodEligibleAndComplete implements Callable<Boolean> {

    private final Application application;
    private final UUID contextId;
    private final UUID deliveryMethodId;

    @Getter
    private boolean successful;

    @Override
    public Boolean call() {
        Context context = application.findContext(contextId);
        successful = context.query(new DeliveryMethodEligible(deliveryMethodId));
        return successful;
    }

}
