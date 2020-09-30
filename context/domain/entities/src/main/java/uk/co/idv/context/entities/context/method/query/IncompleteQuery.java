package uk.co.idv.context.entities.context.method.query;

import uk.co.idv.context.entities.context.method.Method;

import java.util.Optional;
import java.util.stream.Stream;

public class IncompleteQuery<T extends Method> extends QueryDecorator<T> {

    public IncompleteQuery(MethodQuery<T> query) {
        super(query);
    }

    @Override
    public Optional<T> apply(Stream<Method> methods) {
        return super.apply(methods).filter(method -> !method.isComplete());
    }

}
