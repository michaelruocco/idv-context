package uk.co.idv.context.usecases.identity.update;

import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.identity.Identity;

public interface UpdateIdentityRequest {

    Identity getIdentity();

    Channel getChannel();

    Aliases getAliases();

    default String getChannelId() {
        return getChannel().getId();
    }

    UpdateIdentityRequest setIdentity(Identity identity);

}
