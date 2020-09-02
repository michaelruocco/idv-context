package uk.co.idv.policy.adapter.json.key.channelactivity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;

public interface ChannelActivityPolicyKeyMixin {

    @JsonIgnore
    Collection<String> getAliasTypes();

}
