package uk.co.idv.context.entities.context.create;

import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.channel.Channel;

public interface CreateContextRequest {

    Channel getChannel();

    Alias getAlias();

    Activity getActivity();

    default String getChannelId() {
        return getChannel().getId();
    }

    default String getAliasType() {
        return getAlias().getType();
    }

    default String getActivityName() {
        return getActivity().getName();
    }

}
