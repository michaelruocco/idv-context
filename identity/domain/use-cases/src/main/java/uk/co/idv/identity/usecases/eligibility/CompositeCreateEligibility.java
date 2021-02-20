package uk.co.idv.identity.usecases.eligibility;

import uk.co.idv.identity.entities.eligibility.EligibilityNotConfiguredException;
import uk.co.idv.identity.entities.eligibility.IdentityEligibility;
import uk.co.idv.identity.entities.identity.FindIdentityRequest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class CompositeCreateEligibility implements CreateEligibility {

    private final Collection<SupportedCreateEligibility> creates;

    public CompositeCreateEligibility(SupportedCreateEligibility... creates) {
        this(Arrays.asList(creates));
    }

    public CompositeCreateEligibility(Collection<SupportedCreateEligibility> creates) {
        this.creates = creates;
    }

    @Override
    public IdentityEligibility create(FindIdentityRequest request) {
        return select(request)
                .map(create -> create.create(request))
                .orElseThrow(() -> new EligibilityNotConfiguredException(request.getChannelId()));
    }

    private Optional<SupportedCreateEligibility> select(FindIdentityRequest request) {
        return creates.stream()
                .filter(create -> create.supports(request))
                .findFirst();
    }

}
