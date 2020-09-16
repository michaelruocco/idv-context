package uk.co.idv.context.entities.context.method;

import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.policy.method.MethodConfig;

public interface Method {

    String getName();

    Eligibility getEligibility();

    boolean isComplete();

    boolean isSuccessful();

    MethodConfig getConfig();

    default boolean isEligible() {
        return getEligibility().isEligible();
    }

}
