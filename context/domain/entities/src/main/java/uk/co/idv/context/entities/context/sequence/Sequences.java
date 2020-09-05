package uk.co.idv.context.entities.context.sequence;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

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

}
