package uk.co.idv.context.entities.context.method;

import uk.co.idv.method.entities.method.Method;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public interface Methods extends Iterable<Method> {

    boolean isEmpty();

    boolean isEligible();

    boolean isComplete();

    boolean isSuccessful();

    Duration getDuration();

    <T extends Method> Stream<T> streamAsType(Class<T> type);

    Methods getEligibleIncomplete();

    boolean isNext(String name);

    Optional<Method> getNext(String name);

    Optional<Method> getNext();

    Stream<Method> stream();

    Collection<Method> getValues();

    Methods apply(UnaryOperator<Method> function);

}
