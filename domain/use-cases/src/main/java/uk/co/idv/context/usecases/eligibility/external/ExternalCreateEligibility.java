package uk.co.idv.context.usecases.eligibility.external;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.eligibility.Eligibility;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.eligibility.CreateEligibility;
import uk.co.idv.context.usecases.eligibility.CreateEligibilityRequest;
import uk.co.idv.context.usecases.identity.update.UpdateIdentity;

@RequiredArgsConstructor
public class ExternalCreateEligibility implements CreateEligibility {

    private final ExternalFindIdentity find;
    private final UpdateIdentity update;

    @Override
    public Eligibility create(CreateEligibilityRequest request) {
        Identity original = find.find(request);
        Identity updated = update.update(original);
        return Eligibility.builder()
                .channel(request.getChannel())
                .aliases(request.getAliases())
                .requested(request.getRequested())
                .identity(updated)
                .build();
    }

}
