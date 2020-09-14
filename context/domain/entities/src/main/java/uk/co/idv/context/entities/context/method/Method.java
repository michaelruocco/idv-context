package uk.co.idv.context.entities.context.method;

import uk.co.idv.context.entities.context.eligibility.Eligibility;

public interface Method {

    String getName();

    Eligibility getEligibility();

    boolean isComplete();

    boolean isSuccessful();

}
