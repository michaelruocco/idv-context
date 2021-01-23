package uk.co.idv.context.entities.context.sequence.stage;

import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodVerifications;

import java.time.Duration;
import java.util.function.UnaryOperator;

public interface Stage extends Iterable<Method> {

    default boolean isEligible() {
        return getEligibility().isEligible();
    }

    Methods getMethods();

    String getType();

    Eligibility getEligibility();

    Duration getDuration();

    Stage updateMethods(UnaryOperator<Method> function);

    Methods getNextMethods(MethodVerifications verifications);

    boolean isSuccessful(MethodVerifications verifications);

    boolean isComplete(MethodVerifications verifications);

    long completedMethodCount(MethodVerifications verifications);

}
