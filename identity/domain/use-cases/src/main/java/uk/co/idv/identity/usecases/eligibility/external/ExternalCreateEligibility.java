package uk.co.idv.identity.usecases.eligibility.external;

import lombok.Builder;
import uk.co.idv.identity.entities.eligibility.IdentityEligibility;
import uk.co.idv.identity.entities.identity.FindIdentityRequest;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.usecases.eligibility.CreateEligibility;
import uk.co.idv.identity.usecases.eligibility.IdentityEligibilityFactory;
import uk.co.idv.identity.usecases.identity.update.UpdateIdentity;

@Builder
public class ExternalCreateEligibility implements CreateEligibility {

    @Builder.Default
    private final IdentityEligibilityFactory factory = new IdentityEligibilityFactory();

    private final ExternalFindIdentity find;
    private final UpdateIdentity update;

    @Override
    public IdentityEligibility create(FindIdentityRequest request) {
        Identity original = find.find(request);
        Identity updated = update.update(original);
        return factory.build(request, updated);
    }

}
