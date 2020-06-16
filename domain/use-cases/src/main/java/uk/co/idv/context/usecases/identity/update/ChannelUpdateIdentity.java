package uk.co.idv.context.usecases.identity.update;

import lombok.Builder;
import uk.co.idv.context.entities.identity.Identity;

import java.util.Collection;

@Builder
public class ChannelUpdateIdentity implements UpdateIdentity {

    private final Collection<String> supportedChannelIds;
    private final UpdateIdentity update;

    @Override
    public Identity update(UpdateIdentityRequest request) {
        return update.update(request);
    }

    public Collection<String> getSupportedChannelIds() {
        return supportedChannelIds;
    }

}
