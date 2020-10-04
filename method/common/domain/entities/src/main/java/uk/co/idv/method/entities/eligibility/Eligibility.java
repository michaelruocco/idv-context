package uk.co.idv.method.entities.eligibility;

import java.util.Optional;

public interface Eligibility {

    boolean isEligible();

    Optional<String> getReason();

}
