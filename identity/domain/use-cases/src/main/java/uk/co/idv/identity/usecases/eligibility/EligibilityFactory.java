package uk.co.idv.identity.usecases.eligibility;

import uk.co.idv.identity.entities.eligibility.Eligibility;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.FindIdentityRequest;

public class EligibilityFactory {

    public Eligibility build(FindIdentityRequest request, Identity identity) {
        return Eligibility.builder()
                .aliases(request.getAliases())
                .channel(request.getChannel())
                .requestedData(request.getRequestedData())
                .identity(identity)
                .build();
    }

}
