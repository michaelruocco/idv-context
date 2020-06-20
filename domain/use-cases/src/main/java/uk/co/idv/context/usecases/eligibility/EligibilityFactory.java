package uk.co.idv.context.usecases.eligibility;

import uk.co.idv.context.entities.eligibility.Eligibility;
import uk.co.idv.context.entities.identity.Identity;

public class EligibilityFactory {

    public Eligibility build(CreateEligibilityRequest request, Identity identity) {
        return Eligibility.builder()
                .aliases(request.getAliases())
                .channel(request.getChannel())
                .requested(request.getRequested())
                .identity(identity)
                .build();
    }

}
