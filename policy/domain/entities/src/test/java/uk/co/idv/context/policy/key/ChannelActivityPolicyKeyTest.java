package uk.co.idv.context.policy.key;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.policy.PolicyKey;
import uk.co.idv.context.policy.PolicyRequest;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static uk.co.idv.context.policy.key.ChannelActivityPolicyKeyMother.defaultChannelActivityKey;
import static uk.co.idv.context.policy.key.MockPolicyRequestFactory.givenPolicyRequestApplyingTo;

class ChannelActivityPolicyKeyTest {

    @Test
    void shouldReturnType() {
        PolicyKey key = ChannelActivityPolicyKey.builder().build();

        assertThat(key.getType()).isEqualTo("channel-activity");
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

        assertThat(key.getAliasTypes()).containsExactly(PolicyKey.ALL);
    }

    @Test
    void shouldNotApplyToPolicyRequestsWithOtherChannelId() {
        PolicyKey key = defaultChannelActivityKey();
        PolicyRequest request = givenPolicyRequestApplyingTo(key);
        given(request.getChannelId()).willReturn("other-channel");

        boolean applies = key.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldNotApplyToPolicyRequestsWithOtherActivityName() {
        PolicyKey key = defaultChannelActivityKey();
        PolicyRequest request = givenPolicyRequestApplyingTo(key);
        given(request.getActivityName()).willReturn("other-activity");

        boolean applies = key.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldApplyToPolicyRequestsWithMatchingChannelIdAndActivityName() {
        PolicyKey key = defaultChannelActivityKey();
        PolicyRequest request = givenPolicyRequestApplyingTo(key);

        boolean applies = key.appliesTo(request);

        assertThat(applies).isTrue();
    }

}
