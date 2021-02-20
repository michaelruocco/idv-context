package uk.co.idv.context.entities.policy.sequence.stage;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.method.Methods;
import uk.co.idv.context.entities.context.sequence.stage.StageIneligible;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.eligibility.Eligible;
import uk.co.idv.method.entities.method.MethodVerifications;

import java.time.Duration;
import java.util.Collection;

@Data
@RequiredArgsConstructor
public class AllMethodsStageType implements StageType {

    public static final String NAME = "all-methods";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Eligibility calculateEligibility(Methods methods) {
        Collection<String> names = methods.getIneligibleNames();
        if (names.isEmpty()) {
            return new Eligible();
        }
        return new StageIneligible(names);
    }

    @Override
    public Duration calculateDuration(Methods methods) {
        return methods.getTotalDuration();
    }

    @Override
    public Methods calculateNextMethods(Methods methods, MethodVerifications verifications) {
        return methods.getAllIncompleteMethods(verifications);
    }

    @Override
    public boolean calculateSuccessful(Methods methods, MethodVerifications verifications) {
        return methods.allSuccessful(verifications);
    }

    @Override
    public boolean calculateComplete(Methods methods, MethodVerifications verifications) {
        return methods.allComplete(verifications);
    }

}
