package uk.co.idv.context.entities.policy.key;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.PolicyRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static uk.co.idv.context.entities.policy.key.ChannelPolicyKeyMother.defaultChannelKey;
import static uk.co.idv.context.entities.policy.key.MockPolicyRequestFactory.givenPolicyRequestApplyingTo;

class ChannelPolicyKeyTest {

    @Test
    void shouldReturnType() {
        PolicyKey key = ChannelPolicyKey.builder().build();

        assertThat(key.getType()).isEqualTo("channel");
    }

    @Test
    void shouldReturnPriority() {
        int priority = 1;

        PolicyKey key = ChannelPolicyKey.builder()
                .priority(priority)
                .build();

        assertThat(key.getPriority()).isEqualTo(priority);
    }

    @Test
    void shouldReturnChannelId() {
        String channelId = "my-channel";

        PolicyKey key = ChannelPolicyKey.builder()
                .channelId(channelId)
                .build();

        assertThat(key.getChannelId()).isEqualTo(channelId);
    }

    @Test
    void shouldReturnAllActivityName() {
        PolicyKey key = ChannelPolicyKey.builder().build();

        assertThat(key.getActivityNames()).containsExactly(PolicyKeyConstants.ALL);
    }

    @Test
    void shouldReturnAllAliasType() {
        PolicyKey key = ChannelPolicyKey.builder().build();

        assertThat(key.getAliasTypes()).containsExactly(PolicyKeyConstants.ALL);
    }

    @Test
    void shouldNotApplyToPolicyRequestsWithOtherChannelId() {
        PolicyKey key = defaultChannelKey();
        PolicyRequest request = givenPolicyRequestApplyingTo(key);
        given(request.getChannelId()).willReturn("other-channel");

        boolean applies = key.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldApplyToPolicyRequestsWithMatchingChannelId() {
        PolicyKey key = defaultChannelKey();
        PolicyRequest request = givenPolicyRequestApplyingTo(key);

        boolean applies = key.appliesTo(request);

        assertThat(applies).isTrue();
    }

}
