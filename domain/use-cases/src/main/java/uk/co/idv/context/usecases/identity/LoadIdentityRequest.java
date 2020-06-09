package uk.co.idv.context.usecases.identity;

import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.channel.Channel;

public interface LoadIdentityRequest {

    Aliases getAliases();

    Channel getChannel();

}
