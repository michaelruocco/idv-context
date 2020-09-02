package uk.co.idv.identity.usecases.eligibility.external;

import lombok.Builder;
import uk.co.idv.identity.entities.eligibility.Eligibility;
import uk.co.idv.identity.entities.identity.FindIdentityRequest;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.usecases.eligibility.CreateEligibility;
import uk.co.idv.identity.usecases.eligibility.EligibilityFactory;
import uk.co.idv.identity.usecases.identity.update.UpdateIdentity;

@Builder
public class ExternalCreateEligibility implements CreateEligibility {

    @Builder.Default
    private final EligibilityFactory factory = new EligibilityFactory();

    private final ExternalFindIdentity find;
    private final UpdateIdentity update;

    @Override
    public Eligibility create(FindIdentityRequest request) {
        Identity original = find.find(request);
        Identity updated = update.update(original);
        return factory.build(request, updated);
    }

}
