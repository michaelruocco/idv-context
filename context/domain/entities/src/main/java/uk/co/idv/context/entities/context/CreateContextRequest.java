package uk.co.idv.context.entities.context;

import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.channel.Channel;

public interface CreateContextRequest {

    Channel getChannel();

    Alias getAlias();

    Activity getActivity();

}
