package uk.co.idv.context.entities.policy.key;

import uk.co.idv.context.entities.policy.key.ChannelPolicyKey.ChannelPolicyKeyBuilder;

import java.util.UUID;

public interface ChannelPolicyKeyMother {

    static ChannelPolicyKey defaultChannelKey() {
        return builder().build();
    }

    static ChannelPolicyKeyBuilder builder() {
        return ChannelPolicyKey.builder()
                .id(UUID.fromString("805eef78-1933-4da9-ac66-8343b3a6f0d4"))
                .channelId("default-channel")
                .priority(4);
    }

}
