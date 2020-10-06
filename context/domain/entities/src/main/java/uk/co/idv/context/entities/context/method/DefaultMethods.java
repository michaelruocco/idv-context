package uk.co.idv.context.entities.context.method;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.method.Method;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Data
public class DefaultMethods implements Methods {

    private final Collection<Method> values;

    public DefaultMethods(Method... values) {
        this(Arrays.asList(values));
    }

    @Override
    public Iterator<Method> iterator() {
        return values.iterator();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public boolean isEligible() {
        return stream().allMatch(Method::isEligible);
    }

    @Override
    public boolean isComplete() {
        return stream().allMatch(Method::isComplete);
    }

    @Override
    public boolean isSuccessful() {
        return stream().allMatch(Method::isSuccessful);
    }

    @Override
    public Duration getDuration() {
        return stream()
                .map(Method::getDuration)
                .reduce(Duration.ZERO, Duration::plus);
    }

    @Override
    public <T extends Method> Stream<T> streamAsType(Class<T> type) {
        return stream()
                .map(new MethodToType<>(type))
                .flatMap(Optional::stream);
    }

    @Override
    public Methods getEligibleIncomplete() {
        return new DefaultMethods(stream()
                .filter(Method::isEligible)
                .filter(method -> !method.isComplete())
                .collect(Collectors.toList())
        );
    }

    @Override
    public boolean isNext(String name) {
        return getNext(name).isPresent();
    }

    @Override
    public Optional<Method> getNext(String name) {
        return getNext().filter(method -> method.hasName(name));
    }

    @Override
    public Optional<Method> getNext() {
        return stream().filter(method -> !method.isComplete()).findFirst();
    }

    @Override
    public Stream<Method> stream() {
        return values.stream();
    }

    //TODO test
    @Override
    public Methods apply(UnaryOperator<Method> function) {
        return new DefaultMethods(values.stream()
                .map(function)
                .collect(Collectors.toList()));
    }

}
