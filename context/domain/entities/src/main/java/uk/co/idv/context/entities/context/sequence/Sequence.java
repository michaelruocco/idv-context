package uk.co.idv.context.entities.context.sequence;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.sequence.MethodSequence;

import java.time.Duration;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@Builder
@Data
public class Sequence implements MethodSequence, Iterable<Method> {

    private final String name;

    @With
    private final Methods methods;

    @Override
    public Iterator<Method> iterator() {
        return methods.iterator();
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
    public Optional<Method> getNext(String name) {
        return getNext().filter(method -> method.hasName(name));
    }

    @Override
    public Optional<Method> getNext() {
        return stream().filter(method -> !method.isComplete()).findFirst();
    }

    public boolean isSuccessful() {
        return stream().allMatch(Method::isSuccessful);
    }

    public Duration getDuration() {
        return stream()
                .map(Method::getDuration)
                .reduce(Duration.ZERO, Duration::plus);
    }

    public Sequence updateMethods(UnaryOperator<Method> function) {
        return withMethods(methods.updateMethods(function));
    }

    public long getCompletedCount() {
        return stream().filter(Method::isComplete).count();
    }

    public Stream<Method> stream() {
        return methods.stream();
    }

}
