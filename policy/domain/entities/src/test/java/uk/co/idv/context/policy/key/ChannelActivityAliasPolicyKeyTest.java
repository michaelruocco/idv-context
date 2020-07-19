package uk.co.idv.context.policy.key;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.policy.PolicyRequest;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ChannelActivityAliasPolicyKeyTest {

    @Test
    void shouldReturnType() {
        PolicyKey key = ChannelActivityAliasPolicyKey.builder().build();

        assertThat(key.getType()).isEqualTo("channel-activity-alias");
    }

    @Test
    void shouldReturnPriority() {
        int priority = 1;

        PolicyKey key = ChannelActivityAliasPolicyKey.builder()
                .priority(priority)
                .build();

        assertThat(key.getPriority()).isEqualTo(priority);
    }

    @Test
    void shouldReturnChannelId() {
        String channelId = "my-channel";

        PolicyKey key = ChannelActivityAliasPolicyKey.builder()
                .channelId(channelId)
                .build();

        assertThat(key.getChannelId()).isEqualTo(channelId);
    }

    @Test
    void shouldReturnActivityNames() {
        Collection<String> activityNames = Collections.singleton("my-activity");

        PolicyKey key = ChannelActivityAliasPolicyKey.builder()
                .activityNames(activityNames)
                .build();

        assertThat(key.getActivityNames()).isEqualTo(activityNames);
    }

    @Test
    void shouldReturnAliasTypes() {
        Collection<String> aliasTypes = Collections.singleton("my-alias");

        PolicyKey key = ChannelActivityAliasPolicyKey.builder()
                .aliasTypes(aliasTypes)
                .build();

        assertThat(key.getAliasTypes()).isEqualTo(aliasTypes);
    }

    @Test
    void shouldNotApplyToPolicyRequestsWithMatchingChannelIdAndActivityNameOnly() {
        String activityName = "my-activity";
        PolicyKey key = ChannelActivityAliasPolicyKey.builder()
                .channelId("my-channel")
                .activityNames(Collections.singleton(activityName))
                .aliasTypes(Collections.singleton("other-alias"))
                .build();
        PolicyRequest request = mock(PolicyRequest.class);
        given(request.getChannelId()).willReturn(key.getChannelId());
        given(request.getActivityName()).willReturn(activityName);

        boolean applies = key.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldApplyToPolicyRequestsWithMatchingChannelIdAndActivityNameAndAliasType() {
        String aliasType = "my-alias-type";
        String activityName = "my-activity";
        PolicyKey key = ChannelActivityAliasPolicyKey.builder()
                .channelId("my-channel")
                .activityNames(Collections.singleton(activityName))
                .aliasTypes(Collections.singleton(aliasType))
                .build();
        PolicyRequest request = mock(PolicyRequest.class);
        given(request.getChannelId()).willReturn(key.getChannelId());
        given(request.getActivityName()).willReturn(activityName);
        given(request.getAliasType()).willReturn(aliasType);

        boolean applies = key.appliesTo(request);

        assertThat(applies).isTrue();
    }

}
