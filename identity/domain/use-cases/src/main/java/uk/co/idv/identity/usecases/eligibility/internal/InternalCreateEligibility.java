package uk.co.idv.identity.usecases.eligibility.internal;

import lombok.Builder;
import uk.co.idv.identity.entities.eligibility.IdentityEligibility;
import uk.co.idv.identity.entities.identity.FindIdentityRequest;
import uk.co.idv.identity.usecases.eligibility.CreateEligibility;
import uk.co.idv.identity.usecases.eligibility.IdentityEligibilityFactory;
import uk.co.idv.identity.usecases.identity.find.FindIdentity;

@Builder
public class InternalCreateEligibility implements CreateEligibility {

    @Builder.Default
    private final IdentityEligibilityFactory factory = new IdentityEligibilityFactory();
    private final FindIdentity find;

    @Override
    public IdentityEligibility create(FindIdentityRequest request) {
        var identity = find.find(request.getAliases());
        return factory.build(request, identity);
    }

}
