package uk.co.idv.context.usecases.eligibility;

import uk.co.idv.context.entities.eligibility.Eligibility;
import uk.co.idv.context.entities.identity.FindIdentityRequest;

public interface CreateEligibility {

    Eligibility create(FindIdentityRequest request);

}
