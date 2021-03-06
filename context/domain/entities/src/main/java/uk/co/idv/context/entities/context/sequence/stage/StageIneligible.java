package uk.co.idv.context.entities.context.sequence.stage;

import uk.co.idv.method.entities.eligibility.Ineligible;

import java.util.Collection;

public class StageIneligible extends Ineligible {

    public StageIneligible(Collection<String> methodNames) {
        super(toReason(methodNames));
    }

    private static String toReason(Collection<String> methodNames) {
        String singularOrPlural = toSingularOrPlural(methodNames);
        String formattedNames = String.join(", ", methodNames);
        return String.format("Stage has ineligible %s %s", singularOrPlural, formattedNames);
    }

    private static String toSingularOrPlural(Collection<String> methodNames) {
        if (methodNames.size() == 1) {
            return "method";
        }
        return "methods";
    }

}
