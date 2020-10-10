package uk.co.idv.context.entities.context.sequence;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.method.Method;

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

    public boolean isComplete() {
        return stream().anyMatch(Sequence::isComplete);
    }

    public boolean isSuccessful() {
        return stream().anyMatch(Sequence::isSuccessful);
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

    public long getCompletedCount() {
        return stream().filter(Sequence::isComplete).count();
    }

    public long getCompletedMethodCount() {
        return stream().mapToLong(Sequence::getCompletedCount).sum();
    }

}
