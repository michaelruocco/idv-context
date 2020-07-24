package uk.co.idv.context.entities.policy;

public interface PolicyRequestMother {

    static PolicyRequest build() {
        return builder().build();
    }

    static DefaultPolicyRequest.DefaultPolicyRequestBuilder builder() {
        return DefaultPolicyRequest.builder()
                .channelId("default-channel")
                .activityName("default-activity")
                .aliasType("default-alias");
    }

}
