package uk.co.idv.identity.usecases.eligibility;

import lombok.Builder;
import uk.co.idv.identity.entities.eligibility.IdentityEligibility;
import uk.co.idv.identity.entities.identity.FindIdentityRequest;

import java.util.Collection;

@Builder
public class ChannelCreateEligibility implements SupportedCreateEligibility {

    private final CreateEligibility create;
    private final Collection<String> supportedChannelIds;

    @Override
    public IdentityEligibility create(FindIdentityRequest request) {
        return create.create(request);
    }

    @Override
    public boolean supports(FindIdentityRequest request) {
        return supportedChannelIds.contains(request.getChannelId());
    }

}
