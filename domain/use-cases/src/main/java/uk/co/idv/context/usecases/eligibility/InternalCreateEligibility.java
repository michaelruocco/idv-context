package uk.co.idv.context.usecases.eligibility;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.eligibility.Eligibility;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.IdentityRepository;
import uk.co.idv.context.usecases.identity.find.internal.FindIdentity;

@RequiredArgsConstructor
public class InternalCreateEligibility implements CreateEligibility {

    private final FindIdentity find;

    public InternalCreateEligibility(IdentityRepository repository) {
        this(new FindIdentity(repository));
    }

    @Override
    public Eligibility create(CreateEligibilityRequest request) {
        Identity identity = find.find(request.getAliases());
        return Eligibility.builder()
                .aliases(request.getAliases())
                .channel(request.getChannel())
                .requested(request.getRequested())
                .identity(identity)
                .build();
    }

}
