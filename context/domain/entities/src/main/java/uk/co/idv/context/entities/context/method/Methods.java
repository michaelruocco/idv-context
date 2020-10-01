package uk.co.idv.context.entities.context.method;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.method.otp.Otp;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;
import uk.co.idv.context.entities.context.method.query.MethodQuery;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Data
public class Methods implements Iterable<Method> {

    private final Collection<Method> values;

    public Methods(Method... values) {
        this(Arrays.asList(values));
    }

    @Override
    public Iterator<Method> iterator() {
        return values.iterator();
    }

    public boolean isEligible() {
        return values.stream().allMatch(Method::isEligible);
    }

    public boolean isComplete() {
        return values.stream().allMatch(Method::isComplete);
    }

    public boolean isSuccessful() {
        return values.stream().allMatch(Method::isSuccessful);
    }

    public Duration getDuration() {
        return values.stream()
                .map(Method::getDuration)
                .reduce(Duration.ZERO, Duration::plus);
    }

    public <T extends Method> Collection<T> find(MethodQuery<T> query) {
        return query.apply(values.stream()).collect(Collectors.toList());
    }

    public Methods replaceDeliveryMethods(DeliveryMethods newValues) {
        return new Methods(values.stream()
                .map(method -> replaceDeliveryMethodsIfOtp(method, newValues))
                .collect(Collectors.toList()));
    }

    private Method replaceDeliveryMethodsIfOtp(Method method, DeliveryMethods deliveryMethods) {
        if (method instanceof Otp) {
            Otp otp = (Otp) method;
            return otp.replaceDeliveryMethods(deliveryMethods);
        }
        return method;
    }

}
