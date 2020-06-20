package uk.co.idv.context.usecases.eligibility;

import uk.co.idv.context.entities.eligibility.Eligibility;

public interface CreateEligibility {

    Eligibility create(CreateEligibilityRequest request);
}
