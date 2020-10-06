package uk.co.idv.app.manual.context.otp;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.usecases.context.ContextFacade;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Callable;

@Builder
public class DeliveryMethodEligibleAndComplete implements Callable<Boolean> {

    private final ContextFacade contextFacade;
    private final UUID contextId;
    private final UUID deliveryMethodId;

    @Getter
    private boolean successful;

    @Override
    public Boolean call() {
        //TODO see if we can apply similar functional solution to getting next eligible
        //and complete methods in same way we did for updating delivery methods
        Context context = contextFacade.find(contextId);
        Methods methods = context.getNextEligibleIncompleteMethods("one-time-passcode");
        successful = isDeliveryMethodEligibilityComplete(methods);
        return successful;
    }

    private boolean isDeliveryMethodEligibilityComplete(Methods methods) {
        return methods.streamAsType(Otp.class)
                .map(otp -> otp.findDeliveryMethod(deliveryMethodId))
                .flatMap(Optional::stream)
                .filter(DeliveryMethod::isEligible)
                .anyMatch(DeliveryMethod::isEligibilityComplete);
    }

}
