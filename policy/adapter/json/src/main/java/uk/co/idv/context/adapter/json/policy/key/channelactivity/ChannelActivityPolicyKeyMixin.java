package uk.co.idv.context.adapter.json.policy.key.channelactivity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;

public interface ChannelActivityPolicyKeyMixin {

    @JsonIgnore
    Collection<String> getAliasTypes();

}
