package uk.co.idv.method.entities.method;


import uk.co.idv.method.entities.eligibility.Eligibility;

import java.time.Duration;

public interface Method {

    String getName();

    Eligibility getEligibility();

    MethodConfig getConfig();

    default boolean isComplete(MethodVerifications verifications) {
        if (isSuccessful(verifications)) {
            return true;
        }
        return verifications.countByName(getName()) >= getConfig().getMaxNumberOfAttempts();
    }

    default boolean isSuccessful(MethodVerifications verifications) {
        return verifications.containsSuccessful(getName());
    }

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
