package uk.co.idv.context.entities.context.method;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

@RequiredArgsConstructor
public class Methods implements Iterable<Method> {

    private final Collection<Method> values;

    public Methods(Method... values) {
        this(Arrays.asList(values));
    }

    @Override
    public Iterator<Method> iterator() {
        return values.iterator();
    }

}
