package uk.co.idv.context.usecases.identity;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.identity.Identity;

@Builder
@Data
public class DefaultUpsertIdentityRequest implements UpsertIdentityRequest {

    private final Identity identity;
    private final Channel channel;

    @Override
    public Aliases getAliases() {
        return identity.getAliases();
    }

}
