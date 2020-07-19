package uk.co.idv.context.policy.key;

import uk.co.idv.context.policy.key.ChannelActivityAliasPolicyKey.ChannelActivityAliasPolicyKeyBuilder;

import java.util.Collections;

public interface ChannelActivityAliasPolicyKeyMother {

    static ChannelActivityAliasPolicyKey defaultChannelActivityKey() {
        return builder().build();
    }

    static ChannelActivityAliasPolicyKeyBuilder builder() {
        return ChannelActivityAliasPolicyKey.builder()
                .channelId("default-channel")
                .activityNames(Collections.singleton("default-activity"))
                .aliasTypes(Collections.singleton("default-alias"))
                .priority(2);
    }

}
