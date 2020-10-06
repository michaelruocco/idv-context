package uk.co.idv.context.entities.context.sequence;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.method.DefaultMethods;
import uk.co.idv.context.entities.context.method.Methods;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
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

    public Methods getMethodsIfNext(String name) {
        return new DefaultMethods(stream()
                .map(method -> method.getMethodIfNext(name))
                .flatMap(Optional::stream)
                .collect(Collectors.toList())
        );
    }

    public Stream<Sequence> stream() {
        return values.stream();
    }

}
