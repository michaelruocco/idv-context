package uk.co.idv.context.entities.context.sequence;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.method.otp.Otp;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

@RequiredArgsConstructor
public class Sequences implements Iterable<Sequence> {

    private final Collection<Sequence> values;

    public Sequences(Sequence... values) {
        this(Arrays.asList(values));
    }

    @Override
    public Iterator<Sequence> iterator() {
        return values.iterator();
    }

    public Optional<Otp> findNextIncompleteEligibleOtp() {
        return values.stream()
                .map(Sequence::findNextIncompleteEligibleOtp)
                .flatMap(Optional::stream)
                .findFirst();
    }

}
