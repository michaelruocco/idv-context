package uk.co.idv.context.entities.context.sequence.stage;

import lombok.Data;
import lombok.RequiredArgsConstructor;
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

@Data
@RequiredArgsConstructor
public class CompleteAllStage implements Stage {

    @With
    private final Methods methods;

    @Override
    public String getType() {
        return "complete-all";
    }

    @Override
    public Eligibility getEligibility() {
        Collection<String> names = methods.getIneligibleNames();
        if (names.isEmpty()) {
            return new Eligible();
        }
        return new StageIneligible(names);
    }

    @Override
    public Duration getDuration() {
        return methods.getTotalDuration();
    }

    @Override
    public Stage updateMethods(UnaryOperator<Method> function) {
        return withMethods(methods.updateMethods(function));
    }

    @Override
    public Methods getNextMethods(MethodVerifications verifications) {
        return methods.getAllIncompleteMethods(verifications);
    }

    @Override
    public boolean isSuccessful(MethodVerifications verifications) {
        return methods.allSuccessful(verifications);
    }

    @Override
    public boolean isComplete(MethodVerifications verifications) {
        return methods.allComplete(verifications);
    }

    @Override
    public long completedMethodCount(MethodVerifications verifications) {
        return methods.completedCount(verifications);
    }

    @Override
    public Iterator<Method> iterator() {
        return methods.iterator();
    }

}
