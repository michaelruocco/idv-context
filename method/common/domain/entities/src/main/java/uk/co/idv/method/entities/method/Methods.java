package uk.co.idv.method.entities.method;

import java.time.Duration;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public interface Methods extends Iterable<Method> {

    @Override
    Iterator<Method> iterator();

    Stream<Method> stream();

    boolean containsMethod(String methodName);

    boolean allSuccessful(MethodVerifications verifications);

    boolean containsSuccessful(MethodVerifications verifications);

    boolean allComplete(MethodVerifications verifications);

    boolean allIneligible();

    long completedCount(MethodVerifications verifications);

    boolean isEmpty();

    Duration getTotalDuration();

    Duration getShortestDuration();

    Duration getLongestDuration();

    Collection<String> getIneligibleNames();

    Methods getByName(String methodName);

    Methods updateMethods(UnaryOperator<Method> function);

    Methods getAllIncompleteMethods(MethodVerifications verifications);

    Optional<Method> getNextIncompleteMethod(MethodVerifications verifications);

    Collection<Method> getValues();

}
