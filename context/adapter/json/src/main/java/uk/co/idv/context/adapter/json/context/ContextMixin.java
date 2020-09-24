package uk.co.idv.context.adapter.json.context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.identity.Identity;

public interface ContextMixin {

    @JsonIgnore
    Channel getChannel();

    @JsonIgnore
    Activity getActivity();

    @JsonIgnore
    Identity getIdentity();

}
