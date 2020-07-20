package uk.co.idv.context.policy;

public interface PolicyRequestMother {

    static PolicyRequest build() {
        return builder().build();
    }

    static PolicyRequest.PolicyRequestBuilder builder() {
        return PolicyRequest.builder()
                .channelId("default-channel")
                .activityName("default-activity")
                .aliasType("default-alias");
    }

}
