package uk.co.idv.app.manual.context.otp;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.context.entities.context.method.otp.Otp;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethod;
import uk.co.idv.context.usecases.context.ContextFacade;

import java.util.UUID;
import java.util.concurrent.Callable;

import static uk.co.idv.context.entities.context.method.query.MethodQueryFactory.methodOfType;

@Builder
public class DeliveryMethodEligibleAndComplete implements Callable<Boolean> {

    private final ContextFacade contextFacade;
    private final UUID contextId;
    private final UUID deliveryMethodId;

    @Getter
    private boolean successful;

    @Override
    public Boolean call() {
        successful = contextFacade.find(contextId)
                .find(methodOfType(Otp.class))
                .flatMap(otp -> otp.findDeliveryMethod(deliveryMethodId))
                .filter(DeliveryMethod::isEligibilityComplete)
                .map(DeliveryMethod::isEligible)
                .orElse(false);
        return successful;
    }

}
