package uk.co.idv.context.entities.context.method;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodVerifications;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Data
public class Methods implements Iterable<Method> {

    private final Collection<Method> values;

    public Methods(Method... methods) {
        this(Arrays.asList(methods));
    }

    @Override
    public Iterator<Method> iterator() {
        return values.iterator();
    }

    public Stream<Method> stream() {
        return values.stream();
    }

    public boolean containsMethod(String methodName) {
        return !getByName(methodName).isEmpty();
    }

    public boolean allSuccessful(MethodVerifications verifications) {
        return values.stream().allMatch(method -> method.isSuccessful(verifications));
    }

    public boolean allComplete(MethodVerifications verifications) {
        return values.stream().allMatch(method -> method.isComplete(verifications));
    }

    public long completedCount(MethodVerifications verifications) {
        return values.stream().filter(method -> method.isComplete(verifications)).count();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public Duration getTotalDuration() {
        return values.stream()
                .map(Method::getDuration)
                .reduce(Duration.ZERO, Duration::plus);
    }

    public Duration getShortestDuration() {
        return values.stream()
                .map(Method::getDuration)
                .min(Comparator.comparingLong(Duration::toMillis))
                .orElse(Duration.ZERO);
    }

    public Collection<String> getIneligibleNames() {
        return values.stream()
                .filter(method -> !method.isEligible())
                .map(Method::getName)
                .collect(Collectors.toSet());
    }

    public Methods getByName(String methodName) {
        return new Methods(values.stream()
                .filter(method -> method.hasName(methodName))
                .collect(Collectors.toList())
        );
    }

    public Methods updateMethods(UnaryOperator<Method> function) {
        return new Methods(values.stream()
                .map(function)
                .collect(Collectors.toList())
        );
    }

    public Methods getAllIncompleteMethods(MethodVerifications verifications) {
        return new Methods(values.stream()
                .filter(method -> !method.isComplete(verifications))
                .collect(Collectors.toList())
        );
    }

    public Optional<Method> getNextIncompleteMethod(MethodVerifications verifications) {
        return values.stream()
                .filter(method -> !method.isComplete(verifications))
                .findFirst();
    }

}
