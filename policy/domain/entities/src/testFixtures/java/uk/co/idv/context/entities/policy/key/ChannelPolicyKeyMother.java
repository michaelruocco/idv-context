package uk.co.idv.context.entities.policy.key;

import uk.co.idv.context.entities.policy.key.ChannelPolicyKey.ChannelPolicyKeyBuilder;

public interface ChannelPolicyKeyMother {

    static ChannelPolicyKey defaultChannelKey() {
        return builder().build();
    }

    static ChannelPolicyKeyBuilder builder() {
        return ChannelPolicyKey.builder()
                .channelId("default-channel")
                .priority(4);
    }

}
