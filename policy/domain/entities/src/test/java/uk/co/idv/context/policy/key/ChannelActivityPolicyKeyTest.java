package uk.co.idv.context.policy.key;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.policy.PolicyRequest;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ChannelActivityPolicyKeyTest {

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

        assertThat(key.getAliasTypes()).isEmpty();
    }

    @Test
    void shouldNotApplyToPolicyRequestsWithMatchingChannelIdOnly() {
        String channelId = "my-channel";
        PolicyKey key = ChannelActivityPolicyKey.builder()
                .channelId(channelId)
                .activityNames(Collections.singleton("my-activity"))
                .build();
        PolicyRequest request = mock(PolicyRequest.class);
        given(request.getChannelId()).willReturn(channelId);
        given(request.getActivityName()).willReturn("other-activity");

        boolean applies = key.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldApplyToPolicyRequestsWithMatchingChannelIdAndActivityName() {
        String activityName = "my-activity";
        PolicyKey key = ChannelActivityPolicyKey.builder()
                .channelId("my-channel")
                .activityNames(Collections.singleton(activityName))
                .build();
        PolicyRequest request = mock(PolicyRequest.class);
        given(request.getChannelId()).willReturn(key.getChannelId());
        given(request.getActivityName()).willReturn(activityName);

        boolean applies = key.appliesTo(request);

        assertThat(applies).isTrue();
    }

}
