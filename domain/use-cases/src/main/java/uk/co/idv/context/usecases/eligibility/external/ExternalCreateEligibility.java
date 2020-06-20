package uk.co.idv.context.usecases.eligibility.external;

import lombok.Builder;
import uk.co.idv.context.entities.eligibility.Eligibility;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.eligibility.CreateEligibility;
import uk.co.idv.context.usecases.eligibility.CreateEligibilityRequest;
import uk.co.idv.context.usecases.eligibility.EligibilityFactory;
import uk.co.idv.context.usecases.identity.update.UpdateIdentity;

@Builder
public class ExternalCreateEligibility implements CreateEligibility {

    @Builder.Default
    private final EligibilityFactory factory = new EligibilityFactory();

    private final ExternalFindIdentity find;
    private final UpdateIdentity update;

    @Override
    public Eligibility create(CreateEligibilityRequest request) {
        Identity original = find.find(request);
        Identity updated = update.update(original);
        return factory.build(request, updated);
    }

}
