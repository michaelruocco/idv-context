package uk.co.idv.policy.entities.policy.key;

import uk.co.idv.policy.entities.policy.key.ChannelActivityAliasPolicyKey.ChannelActivityAliasPolicyKeyBuilder;

import java.util.Collections;
import java.util.UUID;

public interface ChannelActivityAliasPolicyKeyMother {

    static ChannelActivityAliasPolicyKey build() {
        return builder().build();
    }

    static ChannelActivityAliasPolicyKeyBuilder builder() {
        return ChannelActivityAliasPolicyKey.builder()
                .id(UUID.fromString("fb30f2bc-458f-4ced-bd72-526e9230ff20"))
                .priority(2)
                .channelId("default-channel")
                .activityNames(Collections.singletonList("default-activity"))
                .aliasTypes(Collections.singletonList("default-alias"));
    }

}
