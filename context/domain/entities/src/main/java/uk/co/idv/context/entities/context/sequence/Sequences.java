package uk.co.idv.context.entities.context.sequence;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodVerifications;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Data
public class Sequences implements Iterable<Sequence> {

    private final Collection<Sequence> values;

    public Sequences(Sequence... values) {
        this(Arrays.asList(values));
    }

    @Override
    public Iterator<Sequence> iterator() {
        return values.iterator();
    }

    public boolean isEligible() {
        return stream().anyMatch(Sequence::isEligible);
    }

    public Duration getDuration() {
        return stream()
                .map(Sequence::getDuration)
                .max(Comparator.comparingLong(Duration::toMillis))
                .orElse(Duration.ZERO);
    }

    public Stream<Sequence> stream() {
        return values.stream();
    }

    public Sequences updateMethods(UnaryOperator<Method> function) {
        return new Sequences(values.stream()
                .map(sequence -> sequence.updateMethods(function))
                .collect(Collectors.toList()));
    }

    public boolean isSuccessful(MethodVerifications verifications) {
        return values.stream().anyMatch(sequence -> sequence.isSuccessful(verifications));
    }

    public boolean isComplete(MethodVerifications verifications) {
        return values.stream().anyMatch(sequence -> sequence.isComplete(verifications));
    }

    public Methods getNextMethods(MethodVerifications verifications) {
        return new Methods(values.stream()
                .map(sequence -> sequence.getNextMethods(verifications))
                .flatMap(Methods::stream)
                .collect(Collectors.toList())
        );
    }

    public Collection<String> getNextMethodNames(MethodVerifications verifications) {
        return getNextMethods(verifications).stream()
                .map(Method::getName)
                .collect(Collectors.toList());
    }

    public long completedSequenceCount(MethodVerifications verifications) {
        return values.stream()
                .filter(sequence -> sequence.isComplete(verifications))
                .count();
    }

    public long completedMethodCount(MethodVerifications verifications) {
        return values.stream()
                .map(sequence -> sequence.completedMethodCount(verifications))
                .mapToLong(Long::longValue)
                .sum();
    }

}
