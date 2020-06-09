package uk.co.idv.context.usecases.identity;

import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.channel.Channel;

public interface LoadIdentityRequest {

    Alias getAlias();

    Channel getChannel();

}
