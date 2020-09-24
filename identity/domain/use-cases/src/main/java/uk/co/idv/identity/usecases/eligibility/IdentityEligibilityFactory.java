package uk.co.idv.identity.usecases.eligibility;

import uk.co.idv.identity.entities.eligibility.IdentityEligibility;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.FindIdentityRequest;

public class IdentityEligibilityFactory {

    public IdentityEligibility build(FindIdentityRequest request, Identity identity) {
        return IdentityEligibility.builder()
                .aliases(request.getAliases())
                .channel(request.getChannel())
                .requestedData(request.getRequestedData())
                .identity(identity)
                .build();
    }

}
