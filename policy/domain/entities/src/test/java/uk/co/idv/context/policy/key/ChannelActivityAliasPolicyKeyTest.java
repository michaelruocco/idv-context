package uk.co.idv.context.policy.key;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.policy.PolicyKey;
import uk.co.idv.context.policy.PolicyRequest;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static uk.co.idv.context.policy.key.ChannelActivityAliasPolicyKeyMother.defaultChannelActivityAliasKey;
import static uk.co.idv.context.policy.key.MockPolicyRequestFactory.givenPolicyRequestApplyingTo;

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
        assertThat(key.hasActivityNames()).isTrue();
    }

    @Test
    void shouldReturnAliasTypes() {
        Collection<String> aliasTypes = Collections.singleton("my-alias");

        PolicyKey key = ChannelActivityAliasPolicyKey.builder()
                .aliasTypes(aliasTypes)
                .build();

        assertThat(key.getAliasTypes()).isEqualTo(aliasTypes);
        assertThat(key.hasAliasTypes()).isTrue();
    }

    @Test
    void shouldNotApplyToPolicyRequestsWithOtherChannelId() {
        PolicyKey key = defaultChannelActivityAliasKey();
        PolicyRequest request = givenPolicyRequestApplyingTo(key);
        given(request.getChannelId()).willReturn("other-channel");

        boolean applies = key.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldNotApplyToPolicyRequestsWithOtherActivityName() {
        PolicyKey key = defaultChannelActivityAliasKey();
        PolicyRequest request = givenPolicyRequestApplyingTo(key);
        given(request.getActivityName()).willReturn("other-activity");

        boolean applies = key.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldNotApplyToPolicyRequestsWithOtherAliasType() {
        PolicyKey key = defaultChannelActivityAliasKey();
        PolicyRequest request = givenPolicyRequestApplyingTo(key);
        given(request.getAliasType()).willReturn("other-alias");

        boolean applies = key.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldApplyToPolicyRequestsWithMatchingChannelIdAndActivityNameAndAliasType() {
        PolicyKey key = defaultChannelActivityAliasKey();
        PolicyRequest request = givenPolicyRequestApplyingTo(key);

        boolean applies = key.appliesTo(request);

        assertThat(applies).isTrue();
    }

}
