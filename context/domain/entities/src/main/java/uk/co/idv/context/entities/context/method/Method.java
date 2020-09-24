package uk.co.idv.context.entities.context.method;

import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.policy.method.MethodConfig;

import java.time.Duration;

public interface Method {

    String getName();

    Eligibility getEligibility();

    boolean isComplete();

    boolean isSuccessful();

    MethodConfig getConfig();

    default boolean isEligible() {
        return getEligibility().isEligible();
    }

    default Duration getDuration() {
        if (isEligible()) {
            return getConfig().getDuration();
        }
        return Duration.ZERO;
    }

}
