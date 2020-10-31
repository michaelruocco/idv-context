package uk.co.idv.policy.entities.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.policy.entities.policy.DefaultPolicyRequest.DefaultPolicyRequestBuilder;

import java.util.Collection;
import java.util.Collections;

import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;

class PolicyRequestTest {

    private final DefaultPolicyRequestBuilder builder = DefaultPolicyRequest.builder();

    @Test
    void shouldReturnChannelId() {
        String channelId = "channel-id";

        PolicyRequest request = builder.channelId(channelId).build();

        assertThat(request.getChannelId()).isEqualTo(channelId);
    }

    @Test
    void shouldReturnActivityName() {
        String activityName = "activity-name";

        PolicyRequest request = builder.activityName(activityName).build();

        assertThat(request.getActivityName()).isEqualTo(activityName);
    }

    @Test
    void shouldReturnAliasTypes() {
        Collection<String> aliasTypes = singleton("alias-type");

        PolicyRequest request = builder.aliasTypes(aliasTypes).build();

        assertThat(request.getAliasTypes()).isEqualTo(aliasTypes);
    }


    @Test
    void shouldBeEmptyIfNoParamsSet() {
        DefaultPolicyRequest request = builder.build();

        boolean empty = request.isEmpty();

        assertThat(empty).isTrue();
    }

    @Test
    void shouldNotBeEmptyIfChannelIdSet() {
        DefaultPolicyRequest request = builder.channelId("my-channel").build();

        boolean empty = request.isEmpty();

        assertThat(empty).isFalse();
    }

    @Test
    void shouldNotBeEmptyIfActivityNameSet() {
        DefaultPolicyRequest request = builder.activityName("my-activity").build();

        boolean empty = request.isEmpty();

        assertThat(empty).isFalse();
    }

    @Test
    void shouldBeEmptyIfAliasTypesEmpty() {
        DefaultPolicyRequest request = builder.aliasTypes(Collections.emptyList()).build();

        boolean empty = request.isEmpty();

        assertThat(empty).isTrue();
    }

    @Test
    void shouldBeEmptyIfAliasTypesContainsOnlyNull() {
        DefaultPolicyRequest request = builder.aliasTypes(singleton(null)).build();

        boolean empty = request.isEmpty();

        assertThat(empty).isTrue();
    }

    @Test
    void shouldBeEmptyIfAliasTypesContainsEmptyString() {
        DefaultPolicyRequest request = builder.aliasTypes(singleton("")).build();

        boolean empty = request.isEmpty();

        assertThat(empty).isTrue();
    }

    @Test
    void shouldNotBeEmptyIfAliasTypesSet() {
        DefaultPolicyRequest request = builder.aliasTypes(singleton("my-alias")).build();

        boolean empty = request.isEmpty();

        assertThat(empty).isFalse();
    }

}
