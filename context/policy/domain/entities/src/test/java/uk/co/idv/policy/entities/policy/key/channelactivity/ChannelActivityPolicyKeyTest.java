package uk.co.idv.policy.entities.policy.key.channelactivity;

import org.junit.jupiter.api.Test;
import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.policy.entities.policy.key.PolicyKey;
import uk.co.idv.policy.entities.policy.key.PolicyKeyConstants;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static uk.co.idv.policy.entities.policy.key.channelactivity.ChannelActivityPolicyKeyMother.build;
import static uk.co.idv.policy.entities.policy.key.MockPolicyRequestMother.applyingTo;

class ChannelActivityPolicyKeyTest {

    @Test
    void shouldReturnType() {
        PolicyKey key = ChannelActivityPolicyKey.builder().build();

        assertThat(key.getType()).isEqualTo("channel-activity");
    }

    @Test
    void shouldNotHaveAliasType() {
        PolicyKey key = ChannelActivityPolicyKey.builder().build();

        assertThat(key.hasAliasType()).isFalse();
    }

    @Test
    void shouldReturnId() {
        UUID id = UUID.randomUUID();

        PolicyKey key = ChannelActivityPolicyKey.builder()
                .id(id)
                .build();

        assertThat(key.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnPriority() {
        int priority = 1;

        PolicyKey key = ChannelActivityPolicyKey.builder()
                .priority(priority)
                .build();

        assertThat(key.getPriority()).isEqualTo(priority);
    }

    @Test
    void shouldReturnChannelId() {
        String channelId = "my-channel";

        PolicyKey key = ChannelActivityPolicyKey.builder()
                .channelId(channelId)
                .build();

        assertThat(key.getChannelId()).isEqualTo(channelId);
    }

    @Test
    void shouldReturnActivityNames() {
        Collection<String> activityNames = Collections.singleton("my-activity");

        PolicyKey key = ChannelActivityPolicyKey.builder()
                .activityNames(activityNames)
                .build();

        assertThat(key.getActivityNames()).isEqualTo(activityNames);
    }

    @Test
    void shouldReturnEmptyAliasTypes() {
        PolicyKey key = ChannelActivityPolicyKey.builder().build();

        assertThat(key.getAliasTypes()).containsExactly(PolicyKeyConstants.ALL);
    }

    @Test
    void shouldNotApplyToPolicyRequestsWithOtherChannelId() {
        PolicyKey key = build();
        PolicyRequest request = applyingTo(key);
        given(request.getChannelId()).willReturn("other-channel");

        boolean applies = key.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldNotApplyToPolicyRequestsWithOtherActivityName() {
        PolicyKey key = build();
        PolicyRequest request = applyingTo(key);
        given(request.getActivityName()).willReturn("other-activity");

        boolean applies = key.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldApplyToPolicyRequestsWithMatchingChannelIdAndActivityName() {
        PolicyKey key = build();
        PolicyRequest request = applyingTo(key);

        boolean applies = key.appliesTo(request);

        assertThat(applies).isTrue();
    }

}
