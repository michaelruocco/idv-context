package uk.co.idv.context.entities.context.create;

import uk.co.idv.activity.entities.Activity;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.channel.Channel;

import java.util.Collection;

public interface CreateContextRequest {

    CreateContextRequest withChannel(Channel channel);

    Channel getChannel();

    Aliases getAliases();

    Activity getActivity();

    default String getChannelId() {
        return getChannel().getId();
    }

    default Collection<String> getAliasTypes() {
        return getAliases().getTypes();
    }

    default String getActivityName() {
        return getActivity().getName();
    }

}
