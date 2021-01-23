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
import java.util.Iterator;
import java.util.function.UnaryOperator;

@Data
@RequiredArgsConstructor
public class CompleteAnyStage implements Stage {

    @With
    private final Methods methods;

    @Override
    public String getType() {
        return "complete-any";
    }

    @Override
    public Eligibility getEligibility() {
        if (methods.allIneligible()) {
            return new StageIneligible(methods.getIneligibleNames());
        }
        return new Eligible();
    }

    @Override
    public Duration getDuration() {
        return methods.getLongestDuration();
    }

    @Override
    public Stage updateMethods(UnaryOperator<Method> function) {
        return withMethods(methods.updateMethods(function));
    }

    @Override
    public Methods getNextMethods(MethodVerifications verifications) {
        if (isSuccessful(verifications)) {
            return new Methods();
        }
        return methods.getAllIncompleteMethods(verifications);
    }

    @Override
    public boolean isSuccessful(MethodVerifications verifications) {
        return methods.containsSuccessful(verifications);
    }

    @Override
    public boolean isComplete(MethodVerifications verifications) {
        if (isSuccessful(verifications)) {
            return true;
        }
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
