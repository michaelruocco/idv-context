package uk.co.idv.context.adapter.json.policy.key.channel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;

public interface ChannelPolicyKeyMixin {

    @JsonIgnore
    Collection<String> getActivityNames();

    @JsonIgnore
    Collection<String> getAliasTypes();

}
