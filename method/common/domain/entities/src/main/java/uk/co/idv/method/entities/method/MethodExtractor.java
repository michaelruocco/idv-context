package uk.co.idv.method.entities.method;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MethodExtractor {

    private MethodExtractor() {
        // utility class
    }

    public static <T extends Method> Stream<T> extractByType(Iterable<Method> methods, Class<T> type) {
        return StreamSupport.stream(methods.spliterator(), false)
                .map(method -> new MethodToType<>(type).apply(method))
                .flatMap(Optional::stream);
    }

}
