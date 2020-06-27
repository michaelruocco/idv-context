package uk.co.idv.context.usecases.eligibility;

import lombok.Builder;
import uk.co.idv.context.entities.eligibility.Eligibility;

import java.util.Collection;

@Builder
public class ChannelCreateEligibility implements CreateEligibility {

    private final CreateEligibility create;
    private final Collection<String> supportedChannelIds;

    @Override
    public Eligibility create(CreateEligibilityRequest request) {
        return create.create(request);
    }

    public Collection<String> getSupportedChannelIds() {
        return supportedChannelIds;
    }

}
