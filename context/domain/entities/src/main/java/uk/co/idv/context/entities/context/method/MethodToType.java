package uk.co.idv.context.entities.context.method;

import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.method.Method;

import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public class MethodToType<T extends Method> implements Function<Method, Optional<T>> {

    private final Class<T> type;

    @Override
    public Optional<T> apply(Method method) {
        if (type.isAssignableFrom(method.getClass())) {
            return Optional.of(type.cast(method));
        }
        return Optional.empty();
    }

}
