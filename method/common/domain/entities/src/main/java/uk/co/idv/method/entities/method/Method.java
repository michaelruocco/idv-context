package uk.co.idv.method.entities.method;


import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.result.Result;

import java.time.Duration;

public interface Method {

    String getName();

    Eligibility getEligibility();

    boolean isComplete();

    boolean isSuccessful();

    MethodConfig getConfig();

    Method add(Verification result);

    default boolean hasName(String name) {
        return getName().equals(name);
    }

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
