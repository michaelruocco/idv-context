package uk.co.idv.context.usecases.identity.update;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.identity.Identity;

@Builder
@Data
public class DefaultUpdateIdentityRequest implements UpdateIdentityRequest {

    private final Identity identity;
    private final Channel channel;

    @Override
    public Aliases getAliases() {
        return identity.getAliases();
    }

    @Override
    public UpdateIdentityRequest setIdentity(Identity identity) {
        return DefaultUpdateIdentityRequest.builder()
                .identity(identity)
                .channel(channel)
                .build();
    }

}
