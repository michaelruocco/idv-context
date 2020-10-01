package uk.co.idv.context.entities.context.sequence;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import uk.co.idv.context.entities.context.method.Method;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;
import uk.co.idv.context.entities.context.method.query.MethodQuery;

import java.time.Duration;
import java.util.Collection;

@Builder
@Data
public class Sequence {

    private final String name;

    @With
    private final Methods methods;

    public <T extends Method> Collection<T> find(MethodQuery<T> query) {
        return methods.find(query);
    }

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

    public Sequence replaceOtpDeliveryMethods(DeliveryMethods newValues) {
        return withMethods(methods.replaceDeliveryMethods(newValues));
    }
}
