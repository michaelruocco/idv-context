package uk.co.idv.method.usecases.otp.delivery;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.query.DeliveryMethodExtractor;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

@Builder
public class DeliveryMethodEligibleAndEligibilityComplete implements Callable<Boolean> {

    @Builder.Default
    private final DeliveryMethodExtractor extractor = new DeliveryMethodExtractor();
    private final Supplier<Iterable<Method>> methodsSupplier;
    private final UUID deliveryMethodId;

    @Getter
    private boolean successful;

    @Override
    public Boolean call() {
        DeliveryMethod deliveryMethod = extractor.extract(methodsSupplier.get(), deliveryMethodId);
        successful = deliveryMethod.isEligibilityCompleteAndEligible();
        return successful;
    }

}
