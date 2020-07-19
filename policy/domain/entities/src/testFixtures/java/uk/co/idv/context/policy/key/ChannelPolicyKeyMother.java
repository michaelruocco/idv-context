package uk.co.idv.context.policy.key;

import uk.co.idv.context.policy.key.ChannelPolicyKey.ChannelPolicyKeyBuilder;

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
