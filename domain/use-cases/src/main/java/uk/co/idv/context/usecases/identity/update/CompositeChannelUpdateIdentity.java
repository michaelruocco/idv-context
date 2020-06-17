package uk.co.idv.context.usecases.identity.update;

import uk.co.idv.context.entities.identity.Identity;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CompositeChannelUpdateIdentity implements UpdateIdentity {

    private final Map<String, ChannelUpdateIdentity> updates = new HashMap<>();

    public CompositeChannelUpdateIdentity(ChannelUpdateIdentity... updates) {
        this(Arrays.asList(updates));
    }

    public CompositeChannelUpdateIdentity(Collection<ChannelUpdateIdentity> updates) {
        updates.forEach(this::add);
    }

    @Override
    public Identity update(UpdateIdentityRequest request) {
        String channelId = request.getChannelId();
        return selectUpdate(channelId)
                .map(update -> update.update(request))
                .orElseThrow(() -> new ChannelNotConfiguredForIdentityUpdateException(channelId));
    }

    private void add(ChannelUpdateIdentity update) {
        update.getSupportedChannelIds()
                .forEach(channelId -> updates.put(channelId, update));
    }

    private Optional<UpdateIdentity> selectUpdate(String channelId) {
        return Optional.ofNullable(updates.get(channelId));
    }

}
