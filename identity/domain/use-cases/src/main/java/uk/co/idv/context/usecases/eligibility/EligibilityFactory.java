package uk.co.idv.context.usecases.eligibility;

import uk.co.idv.context.entities.eligibility.Eligibility;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.FindIdentityRequest;

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
