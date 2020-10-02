package uk.co.idv.context.entities.context.method;

import lombok.Data;
import lombok.RequiredArgsConstructor;

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
        return stream().allMatch(Method::isEligible);
    }

    public boolean isComplete() {
        return stream().allMatch(Method::isComplete);
    }

    public boolean isSuccessful() {
        return stream().allMatch(Method::isSuccessful);
    }

    public Duration getDuration() {
        return stream()
                .map(Method::getDuration)
                .reduce(Duration.ZERO, Duration::plus);
    }

    public <T extends Method> Stream<T> streamAsType(Class<T> type) {
        return stream()
                .map(new MethodToType<>(type))
                .flatMap(Optional::stream);
    }

    public Optional<Method> getNext(String name) {
        return getNext().filter(method -> method.hasName(name));
    }

    public Optional<Method> getNext() {
        return stream().filter(method -> !method.isComplete()).findFirst();
    }

    public Methods getEligibleIncomplete() {
        return new Methods(stream()
                .filter(Method::isEligible)
                .filter(method -> !method.isComplete())
                .collect(Collectors.toList())
        );
    }

    public Stream<Method> stream() {
        return values.stream();
    }

}
