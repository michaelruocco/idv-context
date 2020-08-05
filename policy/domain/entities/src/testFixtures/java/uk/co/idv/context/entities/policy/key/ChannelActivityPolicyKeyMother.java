package uk.co.idv.context.entities.policy.key;

import uk.co.idv.context.entities.policy.key.ChannelActivityPolicyKey.ChannelActivityPolicyKeyBuilder;

import java.util.Collections;
import java.util.UUID;

public interface ChannelActivityPolicyKeyMother {

    static ChannelActivityPolicyKey build() {
        return builder().build();
    }

    static ChannelActivityPolicyKeyBuilder builder() {
        return ChannelActivityPolicyKey.builder()
                .id(UUID.fromString("83b6f4ef-f62a-41b3-803a-119622c99072"))
                .priority(3)
                .channelId("default-channel")
                .activityNames(Collections.singletonList("default-activity"));
    }

}
