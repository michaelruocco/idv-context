package uk.co.idv.method.entities.method;

import lombok.Data;
import lombok.RequiredArgsConstructor;

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
public class DefaultMethods implements Methods {

    private final Collection<Method> values;

    public DefaultMethods(Method... methods) {
        this(Arrays.asList(methods));
    }

    @Override
    public Iterator<Method> iterator() {
        return values.iterator();
    }

    @Override
    public Stream<Method> stream() {
        return values.stream();
    }

    @Override
    public boolean containsMethod(String methodName) {
        return !getByName(methodName).isEmpty();
    }

    @Override
    public boolean allSuccessful(MethodVerifications verifications) {
        return values.stream().allMatch(method -> method.isSuccessful(verifications));
    }

    @Override
    public boolean containsSuccessful(MethodVerifications verifications) {
        return values.stream().anyMatch(method -> method.isSuccessful(verifications));
    }

    @Override
    public boolean allComplete(MethodVerifications verifications) {
        return values.stream().allMatch(method -> method.isComplete(verifications));
    }

    @Override
    public boolean allIneligible() {
        return values.stream().noneMatch(Method::isEligible);
    }

    @Override
    public long completedCount(MethodVerifications verifications) {
        return values.stream().filter(method -> method.isComplete(verifications)).count();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public Duration getTotalDuration() {
        return values.stream()
                .map(Method::getDuration)
                .reduce(Duration.ZERO, Duration::plus);
    }

    @Override
    public Duration getShortestDuration() {
        return values.stream()
                .map(Method::getDuration)
                .min(Comparator.comparingLong(Duration::toMillis))
                .orElse(Duration.ZERO);
    }

    @Override
    public Duration getLongestDuration() {
        return values.stream()
                .map(Method::getDuration)
                .max(Comparator.comparingLong(Duration::toMillis))
                .orElse(Duration.ZERO);
    }

    @Override
    public Collection<String> getIneligibleNames() {
        return values.stream()
                .filter(method -> !method.isEligible())
                .map(Method::getName)
                .collect(Collectors.toSet());
    }

    @Override
    public Methods getByName(String methodName) {
        return new DefaultMethods(values.stream()
                .filter(method -> method.hasName(methodName))
                .collect(Collectors.toList())
        );
    }

    @Override
    public Methods updateMethods(UnaryOperator<Method> function) {
        return new DefaultMethods(values.stream()
                .map(function)
                .collect(Collectors.toList())
        );
    }

    @Override
    public Methods getAllIncompleteMethods(MethodVerifications verifications) {
        return new DefaultMethods(values.stream()
                .filter(method -> !method.isComplete(verifications))
                .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<Method> getNextIncompleteMethod(MethodVerifications verifications) {
        return values.stream()
                .filter(method -> !method.isComplete(verifications))
                .findFirst();
    }

}
