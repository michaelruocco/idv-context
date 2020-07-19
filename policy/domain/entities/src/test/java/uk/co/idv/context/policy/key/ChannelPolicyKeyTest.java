package uk.co.idv.context.policy.key;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.policy.PolicyRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

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
    void shouldReturnEmptyActivityNames() {
        PolicyKey key = ChannelPolicyKey.builder().build();

        assertThat(key.getActivityNames()).isEmpty();
    }

    @Test
    void shouldReturnEmptyAliasTypes() {
        PolicyKey key = ChannelPolicyKey.builder().build();

        assertThat(key.getAliasTypes()).isEmpty();
    }

    @Test
    void shouldApplyToPolicyRequestsWithMatchingChannelId() {
        String channelId = "my-channel";
        PolicyRequest request = mock(PolicyRequest.class);
        given(request.getChannelId()).willReturn(channelId);
        PolicyKey key = ChannelPolicyKey.builder()
                .channelId(channelId)
                .build();

        boolean applies = key.appliesTo(request);

        assertThat(applies).isTrue();
    }

    @Test
    void shouldNotApplyToPolicyRequestsWithOtherChannelId() {
        PolicyRequest request = mock(PolicyRequest.class);
        given(request.getChannelId()).willReturn("other-channel");
        PolicyKey key = ChannelPolicyKey.builder()
                .channelId("my-channel")
                .build();

        boolean applies = key.appliesTo(request);

        assertThat(applies).isFalse();
    }

}
