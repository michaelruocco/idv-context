package uk.co.idv.identity.usecases.eligibility;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.eligibility.IdentityEligibility;
import uk.co.idv.identity.entities.identity.FindIdentityRequest;

@RequiredArgsConstructor
public class DefaultCreateEligibility implements SupportedCreateEligibility {

    private final CreateEligibility create;

    @Override
    public IdentityEligibility create(FindIdentityRequest request) {
        return create.create(request);
    }

    @Override
    public boolean supports(FindIdentityRequest request) {
        return true;
    }

}
