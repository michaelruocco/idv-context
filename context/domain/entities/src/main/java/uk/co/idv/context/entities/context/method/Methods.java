package uk.co.idv.context.entities.context.method;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.method.otp.Otp;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

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

    public Optional<Otp> findNextIncompleteEligibleOtp() {
        return findNextIncompleteEligibleMethodOfType(Otp.class);
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

    public <T> Optional<T> findNextIncompleteEligibleMethodOfType(Class<T> type) {
        return values.stream()
                .filter(method -> !method.isComplete())
                .filter(Method::isEligible)
                .filter(method -> type.isAssignableFrom(method.getClass()))
                .map(type::cast)
                .findFirst();
    }

}
