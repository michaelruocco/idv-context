package uk.co.idv.context.entities.context.sequence;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import uk.co.idv.context.entities.context.method.Method;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;

import java.time.Duration;
import java.util.Optional;

@Builder
@Data
public class Sequence {

    private final String name;

    @With
    private final Methods methods;

    public boolean isEligible() {
        return methods.isEligible();
    }

    public boolean isComplete() {
        return methods.isComplete();
    }

    public boolean isSuccessful() {
        return methods.isSuccessful();
    }

    public Duration getDuration() {
        return methods.getDuration();
    }

    //TODO split into separate class
    public Sequence replaceOtpDeliveryMethods(DeliveryMethods newValues) {
        return withMethods(methods.replaceDeliveryMethods(newValues));
    }

    public Optional<Method> getMethodIfNext(String name) {
        return methods.getNext(name);
    }

}
