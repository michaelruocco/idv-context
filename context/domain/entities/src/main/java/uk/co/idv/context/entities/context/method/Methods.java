package uk.co.idv.context.entities.context.method;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.method.Method;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
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

    public Methods updateMethods(UnaryOperator<Method> function) {
        return new Methods(values.stream()
                .map(function)
                .collect(Collectors.toList())
        );
    }

}
