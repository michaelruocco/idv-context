package uk.co.idv.context.entities.policy.key;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.PolicyRequest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static uk.co.idv.context.entities.policy.key.ChannelPolicyKeyMother.build;
import static uk.co.idv.context.entities.policy.key.MockPolicyRequestMother.applyingTo;

class ChannelPolicyKeyTest {

    @Test
    void shouldReturnType() {
        PolicyKey key = ChannelPolicyKey.builder().build();

        assertThat(key.getType()).isEqualTo("channel");
    }

    @Test
    void shouldNotHaveAliasType() {
        PolicyKey key = ChannelPolicyKey.builder().build();

        assertThat(key.hasAliasType()).isFalse();
    }

    @Test
    void shouldReturnId() {
        UUID id = UUID.randomUUID();

        PolicyKey key = ChannelPolicyKey.builder()
                .id(id)
                .build();

        assertThat(key.getId()).isEqualTo(id);
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
        PolicyKey key = build();
        PolicyRequest request = applyingTo(key);
        given(request.getChannelId()).willReturn("other-channel");

        boolean applies = key.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldApplyToPolicyRequestsWithMatchingChannelId() {
        PolicyKey key = build();
        PolicyRequest request = applyingTo(key);

        boolean applies = key.appliesTo(request);

        assertThat(applies).isTrue();
    }

}
