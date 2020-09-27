package uk.co.idv.identity.entities.identity;

import lombok.Builder;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.channel.Channel;

@Builder
public class FakeFindIdentityRequest implements FindIdentityRequest {

    private final Aliases aliases;
    private final RequestedData requestedData;
    private final Channel channel;

    @Override
    public Aliases getAliases() {
        return aliases;
    }

    @Override
    public RequestedData getRequestedData() {
        return requestedData;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

}
