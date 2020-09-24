package uk.co.idv.identity.usecases.eligibility;

import uk.co.idv.identity.entities.eligibility.IdentityEligibility;
import uk.co.idv.identity.entities.identity.FindIdentityRequest;

public interface CreateEligibility {

    IdentityEligibility create(FindIdentityRequest request);

}
