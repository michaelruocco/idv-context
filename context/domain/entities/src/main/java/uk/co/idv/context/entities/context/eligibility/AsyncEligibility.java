package uk.co.idv.context.entities.context.eligibility;

import java.util.Optional;

public interface Eligibility {

    boolean isEligible();

    Optional<String> getReason();

}
