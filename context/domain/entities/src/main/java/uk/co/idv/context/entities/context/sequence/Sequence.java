package uk.co.idv.context.entities.context.sequence;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.sequence.stage.Stage;
import uk.co.idv.context.entities.context.sequence.stage.Stages;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.eligibility.Eligible;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodVerifications;

import java.time.Duration;
import java.util.Iterator;
import java.util.function.UnaryOperator;

@Builder
@Data
public class Sequence implements Iterable<Stage> {

    private final String name;

    @With
    private final Stages stages;

    @Override
    public Iterator<Stage> iterator() {
        return stages.iterator();
    }

    public boolean isEligible() {
        return getEligibility().isEligible();
    }

    public Duration getDuration() {
        return stages.getTotalDuration();
    }

    public Sequence updateMethods(UnaryOperator<Method> function) {
        return withStages(stages.updateMethods(function));
    }

    public Eligibility getEligibility() {
        if (stages.allEligible()) {
            return new Eligible();
        }
        return new SequenceIneligible(stages.getIneligibleMethodNames());
    }

    public Methods getNextIncompleteMethods(MethodVerifications verifications) {
        return stages.getNextIncompleteMethods(verifications);
    }

    public boolean isSuccessful(MethodVerifications verifications) {
        return stages.allSuccessful(verifications);
    }

    public boolean isComplete(MethodVerifications verifications) {
        return stages.allComplete(verifications);
    }

    public long completedMethodCount(MethodVerifications verifications) {
        return stages.completedCount(verifications);
    }

}
