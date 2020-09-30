package uk.co.idv.context.entities.context.method.query;

import lombok.Data;
import uk.co.idv.context.entities.context.method.Method;

import java.util.Optional;
import java.util.stream.Stream;

@Data
public class FindMethodOfTypeQuery<T extends Method> implements MethodQuery<T> {

    private final Class<T> type;

    @Override
    public Optional<T> apply(Stream<Method> methods) {
        return methods.filter(method -> type.isAssignableFrom(method.getClass()))
                .map(type::cast)
                .findFirst();
    }

    @Override
    public Optional<MethodQuery<T>> getParent() {
        return Optional.empty();
    }

}
