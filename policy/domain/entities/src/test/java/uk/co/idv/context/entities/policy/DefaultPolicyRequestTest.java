package uk.co.idv.context.entities.policy;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;

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
    void shouldReturnAliasTypes() {
        Collection<String> aliasTypes = Collections.singleton("my-alias");

        PolicyRequest request = DefaultPolicyRequest.builder()
                .aliasTypes(aliasTypes)
                .build();

        assertThat(request.getAliasTypes()).isEqualTo(aliasTypes);
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
    void shouldBeEmptyIfAliasTypesEmpty() {
        DefaultPolicyRequest request = DefaultPolicyRequest.builder()
                .aliasTypes(Collections.emptyList())
                .build();

        assertThat(request.isEmpty()).isTrue();
    }

    @Test
    void shouldBeEmptyIfAliasTypesContainsOnlyNull() {
        DefaultPolicyRequest request = DefaultPolicyRequest.builder()
                .aliasTypes(Collections.singleton(null))
                .build();

        assertThat(request.isEmpty()).isTrue();
    }

    @Test
    void shouldBeEmptyIfAliasTypesContainsEmptyString() {
        DefaultPolicyRequest request = DefaultPolicyRequest.builder()
                .aliasTypes(Collections.singleton(""))
                .build();

        assertThat(request.isEmpty()).isTrue();
    }

    @Test
    void shouldNotBeEmptyIfAliasTypesSet() {
        DefaultPolicyRequest request = DefaultPolicyRequest.builder()
                .aliasTypes(Collections.singleton("my-alias"))
                .build();

        assertThat(request.isEmpty()).isFalse();
    }

}
