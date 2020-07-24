package uk.co.idv.context.entities.policy.key;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.PolicyRequest;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static uk.co.idv.context.entities.policy.key.ChannelActivityAliasPolicyKeyMother.defaultChannelActivityAliasKey;
import static uk.co.idv.context.entities.policy.key.MockPolicyRequestMother.applyingTo;

class ChannelActivityAliasPolicyKeyTest {

    @Test
    void shouldReturnType() {
        PolicyKey key = ChannelActivityAliasPolicyKey.builder().build();

        assertThat(key.getType()).isEqualTo("channel-activity-alias");
    }

    @Test
    void shouldHaveAliasType() {
        PolicyKey key = ChannelActivityAliasPolicyKey.builder().build();

        assertThat(key.hasAliasType()).isTrue();
    }

    @Test
    void shouldReturnId() {
        UUID id = UUID.randomUUID();

        PolicyKey key = ChannelActivityAliasPolicyKey.builder()
                .id(id)
                .build();

        assertThat(key.getId()).isEqualTo(id);
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
    void shouldNotApplyToPolicyRequestsWithOtherChannelId() {
        PolicyKey key = defaultChannelActivityAliasKey();
        PolicyRequest request = applyingTo(key);
        given(request.getChannelId()).willReturn("other-channel");

        boolean applies = key.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldNotApplyToPolicyRequestsWithOtherActivityName() {
        PolicyKey key = defaultChannelActivityAliasKey();
        PolicyRequest request = applyingTo(key);
        given(request.getActivityName()).willReturn("other-activity");

        boolean applies = key.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldNotApplyToPolicyRequestsWithOtherAliasType() {
        PolicyKey key = defaultChannelActivityAliasKey();
        PolicyRequest request = applyingTo(key);
        given(request.getAliasType()).willReturn("other-alias");

        boolean applies = key.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldApplyToPolicyRequestsWithMatchingChannelIdAndActivityNameAndAliasType() {
        PolicyKey key = defaultChannelActivityAliasKey();
        PolicyRequest request = applyingTo(key);

        boolean applies = key.appliesTo(request);

        assertThat(applies).isTrue();
    }

}
