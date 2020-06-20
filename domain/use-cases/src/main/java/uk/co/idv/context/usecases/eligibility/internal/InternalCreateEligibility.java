package uk.co.idv.context.usecases.eligibility.internal;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.eligibility.Eligibility;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.eligibility.CreateEligibility;
import uk.co.idv.context.usecases.eligibility.CreateEligibilityRequest;
import uk.co.idv.context.usecases.eligibility.EligibilityFactory;
import uk.co.idv.context.usecases.identity.IdentityRepository;
import uk.co.idv.context.usecases.identity.find.FindIdentity;

@RequiredArgsConstructor
public class InternalCreateEligibility implements CreateEligibility {

    private final FindIdentity find;
    private final EligibilityFactory factory;

    public InternalCreateEligibility(FindIdentity find) {
        this(find, new EligibilityFactory());
    }

    public InternalCreateEligibility(IdentityRepository repository) {
        this(new FindIdentity(repository));
    }

    @Override
    public Eligibility create(CreateEligibilityRequest request) {
        Identity identity = find.find(request.getAliases());
        return factory.build(request, identity);
    }

}
