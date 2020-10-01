package uk.co.idv.context.entities.context.method.query;

import lombok.Data;
import uk.co.idv.context.entities.context.method.Method;

import java.util.Optional;
import java.util.stream.Stream;

@Data
public class QueryDecorator<T extends Method> implements MethodQuery<T> {

    private final MethodQuery<T> query;

    @Override
    public Stream<T> apply(Stream<Method> methods) {
        return query.apply(methods);
    }

    @Override
    public Class<T> getType() {
        return query.getType();
    }

    @Override
    public Optional<MethodQuery<T>> getParent() {
        return Optional.of(query);
    }

}
