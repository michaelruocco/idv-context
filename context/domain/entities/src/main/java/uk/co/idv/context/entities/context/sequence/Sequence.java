package uk.co.idv.context.entities.context.sequence;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.eligibility.Eligible;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodVerifications;

import java.time.Duration;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.UnaryOperator;

@Builder
@Data
public class Sequence implements Iterable<Method> {

    private final String name;

    @With
    private final Methods methods;

    @Override
    public Iterator<Method> iterator() {
        return methods.iterator();
    }

    public boolean isEligible() {
        return getEligibility().isEligible();
    }

    public Duration getDuration() {
        return methods.getTotalDuration();
    }

    public Sequence updateMethods(UnaryOperator<Method> function) {
        return withMethods(methods.updateMethods(function));
    }

    public Eligibility getEligibility() {
        Collection<String> names = methods.getIneligibleNames();
        if (names.isEmpty()) {
            return new Eligible();
        }
        return new SequenceIneligible(names);
    }

    public Methods getNextMethods(MethodVerifications verifications) {
        return methods.getNextIncompleteMethod(verifications)
                .map(Methods::new)
                .orElse(new Methods());
    }

    public boolean isSuccessful(MethodVerifications verifications) {
        return methods.allSuccessful(verifications);
    }

    public boolean isComplete(MethodVerifications verifications) {
        return methods.allComplete(verifications);
    }

    public long completedMethodCount(MethodVerifications verifications) {
        return methods.completedCount(verifications);
    }

}
