package uk.co.idv.context.usecases.eligibility.internal;

import lombok.Builder;
import uk.co.idv.context.entities.eligibility.Eligibility;
import uk.co.idv.context.entities.identity.FindIdentityRequest;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.eligibility.CreateEligibility;
import uk.co.idv.context.usecases.eligibility.EligibilityFactory;
import uk.co.idv.context.usecases.identity.IdentityRepository;
import uk.co.idv.context.usecases.identity.find.FindIdentity;

@Builder
public class InternalCreateEligibility implements CreateEligibility {

    @Builder.Default
    private final EligibilityFactory factory = new EligibilityFactory();
    private final FindIdentity find;

    public static CreateEligibility build(IdentityRepository repository) {
        return InternalCreateEligibility.builder()
                .find(new FindIdentity(repository))
                .build();
    }

    @Override
    public Eligibility create(FindIdentityRequest request) {
        Identity identity = find.find(request.getAliases());
        return factory.build(request, identity);
    }

}
