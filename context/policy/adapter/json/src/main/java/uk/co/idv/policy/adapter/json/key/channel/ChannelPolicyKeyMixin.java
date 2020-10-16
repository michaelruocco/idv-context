package uk.co.idv.policy.adapter.json.key.channel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;

public interface ChannelPolicyKeyMixin {

    @JsonIgnore
    Collection<String> getActivityNames();

    @JsonIgnore
    Collection<String> getAliasTypes();

}
