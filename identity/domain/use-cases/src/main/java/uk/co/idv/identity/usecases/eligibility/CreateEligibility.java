package uk.co.idv.identity.usecases.eligibility;

import uk.co.idv.identity.entities.eligibility.Eligibility;
import uk.co.idv.identity.entities.identity.FindIdentityRequest;

public interface CreateEligibility {

    Eligibility create(FindIdentityRequest request);

}
