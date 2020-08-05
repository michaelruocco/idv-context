package uk.co.idv.context.entities.policy;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultPolicyRequestTest {

    @Test
    void shouldReturnChannelId() {
        String channelId = "my-channel";

        PolicyRequest request = DefaultPolicyRequest.builder()
                .channelId(channelId)
                .build();

        assertThat(request.getChannelId()).isEqualTo(channelId);
    }

    @Test
    void shouldReturnActivityName() {
        String activityName = "my-activity";

        PolicyRequest request = DefaultPolicyRequest.builder()
                .activityName(activityName)
                .build();

        assertThat(request.getActivityName()).isEqualTo(activityName);
    }

    @Test
    void shouldReturnAliasType() {
        String aliasType = "my-alias";

        PolicyRequest request = DefaultPolicyRequest.builder()
                .aliasType(aliasType)
                .build();

        assertThat(request.getAliasType()).isEqualTo(aliasType);
    }

    @Test
    void shouldBeEmptyIfNoParamsSet() {
        DefaultPolicyRequest request = DefaultPolicyRequest.builder().build();

        assertThat(request.isEmpty()).isTrue();
    }

    @Test
    void shouldNotBeEmptyIfChannelIdSet() {
        DefaultPolicyRequest request = DefaultPolicyRequest.builder()
                .channelId("my-channel")
                .build();

        assertThat(request.isEmpty()).isFalse();
    }

    @Test
    void shouldNotBeEmptyIfActivityNameSet() {
        DefaultPolicyRequest request = DefaultPolicyRequest.builder()
                .activityName("my-activity")
                .build();

        assertThat(request.isEmpty()).isFalse();
    }

    @Test
    void shouldNotBeEmptyIfAliasTypeSet() {
        DefaultPolicyRequest request = DefaultPolicyRequest.builder()
                .aliasType("my-alias")
                .build();

        assertThat(request.isEmpty()).isFalse();
    }

}
