package uk.co.idv.context.usecases.identity.request;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.UpdateIdentityRequest;

@Builder
@Data
public class DefaultUpdateIdentityRequest implements UpdateIdentityRequest {

    private final Identity identity;
    private final Channel channel;

    @Override
    public Aliases getAliases() {
        return identity.getAliases();
    }

}
