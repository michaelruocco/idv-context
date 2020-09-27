package uk.co.idv.identity.usecases.eligibility;

import uk.co.idv.identity.entities.identity.FindIdentityRequest;

public interface SupportedCreateEligibility extends CreateEligibility {

     boolean supports(FindIdentityRequest request);

}
