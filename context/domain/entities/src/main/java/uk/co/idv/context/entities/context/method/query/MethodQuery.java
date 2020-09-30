package uk.co.idv.context.entities.context.method.query;

import uk.co.idv.context.entities.context.method.Method;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public interface MethodQuery<T extends Method> extends Function<Stream<Method>, Optional<T>> {

    Class<T> getType();

    Optional<MethodQuery<T>> getParent();

}
