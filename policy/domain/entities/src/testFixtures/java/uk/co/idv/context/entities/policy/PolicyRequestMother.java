package uk.co.idv.context.entities.policy;

import java.util.Collections;

public interface PolicyRequestMother {

    static PolicyRequest build() {
        return builder().build();
    }

    static DefaultPolicyRequest.DefaultPolicyRequestBuilder builder() {
        return DefaultPolicyRequest.builder()
                .channelId("default-channel")
                .activityName("default-activity")
                .aliasTypes(Collections.singleton("default-alias"));
    }

}
