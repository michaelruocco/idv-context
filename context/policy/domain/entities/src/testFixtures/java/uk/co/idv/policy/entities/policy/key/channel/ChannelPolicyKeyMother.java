package uk.co.idv.policy.entities.policy.key.channel;

import uk.co.idv.policy.entities.policy.key.channel.ChannelPolicyKey.ChannelPolicyKeyBuilder;

import java.util.UUID;

public interface ChannelPolicyKeyMother {

    static ChannelPolicyKey build() {
        return builder().build();
    }

    static ChannelPolicyKey withId(UUID id) {
        return builder().id(id).build();
    }

    static ChannelPolicyKey withChannelId(String channelId) {
        return builder().channelId(channelId).build();
    }

    static ChannelPolicyKeyBuilder builder() {
        return ChannelPolicyKey.builder()
                .id(UUID.fromString("805eef78-1933-4da9-ac66-8343b3a6f0d4"))
                .priority(4)
                .channelId("default-channel");
    }

}
