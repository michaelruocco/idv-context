package uk.co.idv.context.entities.context.method;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.method.otp.Otp;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public boolean isEmpty() {
        return values.isEmpty();
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

    //TODO split into separate class
    public Methods replaceDeliveryMethods(DeliveryMethods newValues) {
        return new Methods(values.stream()
                .map(method -> replaceDeliveryMethodsIfOtp(method, newValues))
                .collect(Collectors.toList()));
    }

    public <T extends Method> Stream<T> streamAsType(Class<T> type) {
        return values.stream()
                .map(new MethodToType<>(type))
                .flatMap(Optional::stream);
    }

    public Optional<Method> getNext(String name) {
        return getNext().filter(method -> method.hasName(name));
    }

    public Optional<Method> getNext() {
        return values.stream().filter(method -> !method.isComplete()).findFirst();
    }

    public Methods getEligibleIncomplete() {
        return new Methods(values.stream()
                .filter(Method::isEligible)
                .filter(method -> !method.isComplete())
                .collect(Collectors.toList())
        );
    }

    //TODO split into separate class
    private Method replaceDeliveryMethodsIfOtp(Method method, DeliveryMethods deliveryMethods) {
        if (method instanceof Otp) {
            Otp otp = (Otp) method;
            return otp.replaceDeliveryMethods(deliveryMethods);
        }
        return method;
    }

}
