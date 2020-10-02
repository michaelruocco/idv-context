package uk.co.idv.context.entities.context.sequence;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return values.stream().anyMatch(Sequence::isEligible);
    }

    public boolean isComplete() {
        return values.stream().anyMatch(Sequence::isComplete);
    }

    public boolean isSuccessful() {
        return values.stream().anyMatch(Sequence::isSuccessful);
    }

    public Duration getDuration() {
        return values.stream()
                .map(Sequence::getDuration)
                .max(Comparator.comparingLong(Duration::toMillis))
                .orElse(Duration.ZERO);
    }

    //TODO split into separate class
    public Sequences replaceDeliveryMethods(DeliveryMethods newValues) {
        return new Sequences(values.stream()
                .map(sequence -> sequence.replaceOtpDeliveryMethods(newValues))
                .collect(Collectors.toList()));
    }

    public Methods getMethodsIfNext(String name) {
        return new Methods(values.stream()
                .map(sequence -> sequence.getMethodIfNext(name))
                .flatMap(Optional::stream)
                .collect(Collectors.toList())
        );
    }

}
