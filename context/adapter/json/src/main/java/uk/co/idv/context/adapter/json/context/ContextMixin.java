package uk.co.idv.context.adapter.json.context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.identity.Identity;

import java.time.Duration;
import java.util.Collection;

public interface ContextMixin {

    @JsonIgnore
    Channel getChannel();

    @JsonIgnore
    String getChannelId();

    @JsonIgnore
    Activity getActivity();

    @JsonIgnore
    String getActivityName();

    @JsonIgnore
    Identity getIdentity();

    @JsonIgnore
    IdvId getIdvId();

    @JsonIgnore
    Aliases getAliases();

    @JsonIgnore
    Collection<String> getAliasTypes();

    @JsonIgnore
    Duration getDuration();

}
