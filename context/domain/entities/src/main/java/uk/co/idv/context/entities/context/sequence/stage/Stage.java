package uk.co.idv.context.entities.context.sequence.stage;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.policy.sequence.stage.StageType;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodVerifications;

import java.time.Duration;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.UnaryOperator;

@Data
@Builder
public class Stage implements Iterable<Method> {

    public final StageType type;

    @With
    private final Methods methods;

    @Override
    public Iterator<Method> iterator() {
        return methods.iterator();
    }

    public String getTypeName() {
        return type.getName();
    }

    public boolean isEligible() {
        return getEligibility().isEligible();
    }

    public Eligibility getEligibility() {
        return type.calculateEligibility(methods);
    }

    public Duration getDuration() {
        return type.calculateDuration(methods);
    }

    public boolean containsMethod(String methodName) {
        return methods.containsMethod(methodName);
    }

    public Collection<String> getIneligibleMethodNames() {
        return methods.getIneligibleNames();
    }

    public Stage updateMethods(UnaryOperator<Method> function) {
        return withMethods(methods.updateMethods(function));
    }

    public Methods getNextMethods(MethodVerifications verifications) {
        return type.calculateNextMethods(methods, verifications);
    }

    public boolean isSuccessful(MethodVerifications verifications) {
        return type.calculateSuccessful(methods, verifications);
    }

    public boolean isComplete(MethodVerifications verifications) {
        return type.calculateComplete(methods, verifications);
    }

    public long completedMethodCount(MethodVerifications verifications) {
        return methods.completedCount(verifications);
    }

}
