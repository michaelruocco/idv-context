package uk.co.idv.context.entities.policy.key;

import uk.co.idv.context.entities.policy.key.ChannelActivityPolicyKey.ChannelActivityPolicyKeyBuilder;

import java.util.Collections;

public interface ChannelActivityPolicyKeyMother {

    static ChannelActivityPolicyKey defaultChannelActivityKey() {
        return builder().build();
    }

    static ChannelActivityPolicyKeyBuilder builder() {
        return ChannelActivityPolicyKey.builder()
                .channelId("default-channel")
                .activityNames(Collections.singleton("default-activity"))
                .priority(3);
    }

}
