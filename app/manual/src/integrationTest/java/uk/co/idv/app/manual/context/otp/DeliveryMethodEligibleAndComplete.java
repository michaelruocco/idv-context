package uk.co.idv.app.manual.context.otp;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.context.entities.context.method.otp.Otp;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethod;
import uk.co.idv.context.usecases.context.ContextFacade;

import java.util.UUID;
import java.util.concurrent.Callable;

import static uk.co.idv.context.entities.context.method.query.MethodQueryFactory.incompleteAndEligible;


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
                .find(incompleteAndEligible(Otp.class))
                .flatMap(otp -> otp.findDeliveryMethod(deliveryMethodId))
                .filter(DeliveryMethod::isEligible)
                .map(DeliveryMethod::isEligibilityComplete)
                .orElse(false);
        return successful;
    }

}
