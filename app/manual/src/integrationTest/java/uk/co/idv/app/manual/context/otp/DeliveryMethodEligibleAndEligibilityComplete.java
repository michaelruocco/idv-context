package uk.co.idv.app.manual.context.otp;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.app.manual.Application;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.query.DeliveryMethodExtractor;

import java.util.UUID;
import java.util.concurrent.Callable;

@Builder
public class DeliveryMethodEligibleAndEligibilityComplete implements Callable<Boolean> {

    @Builder.Default
    private final DeliveryMethodExtractor extractor = new DeliveryMethodExtractor();
    private final Application application;
    private final UUID contextId;
    private final UUID deliveryMethodId;

    @Getter
    private boolean successful;

    @Override
    public Boolean call() {
        Context context = application.findContext(contextId);
        DeliveryMethod deliveryMethod = extractor.extract(context, deliveryMethodId);
        successful = deliveryMethod.isEligibilityCompleteAndEligible();
        return successful;
    }

}
