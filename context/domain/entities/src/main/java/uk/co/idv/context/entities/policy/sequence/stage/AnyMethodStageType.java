package uk.co.idv.context.entities.policy.sequence.stage;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.method.Methods;
import uk.co.idv.context.entities.context.sequence.stage.StageIneligible;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.eligibility.Eligible;
import uk.co.idv.method.entities.method.MethodVerifications;

import java.time.Duration;

@Data
@RequiredArgsConstructor
public class AnyMethodStageType implements StageType {

    public static final String NAME = "any-method";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Eligibility calculateEligibility(Methods methods) {
        if (methods.allIneligible()) {
            return new StageIneligible(methods.getIneligibleNames());
        }
        return new Eligible();
    }

    @Override
    public Duration calculateDuration(Methods methods) {
        return methods.getLongestDuration();
    }

    @Override
    public Methods calculateNextMethods(Methods methods, MethodVerifications verifications) {
        if (calculateSuccessful(methods, verifications)) {
            return new Methods();
        }
        return methods.getAllIncompleteMethods(verifications);
    }

    @Override
    public boolean calculateSuccessful(Methods methods, MethodVerifications verifications) {
        return methods.containsSuccessful(verifications);
    }

    @Override
    public boolean calculateComplete(Methods methods, MethodVerifications verifications) {
        if (calculateSuccessful(methods, verifications)) {
            return true;
        }
        return methods.allComplete(verifications);
    }

}
