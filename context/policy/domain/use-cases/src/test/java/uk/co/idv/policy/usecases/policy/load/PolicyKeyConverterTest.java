package uk.co.idv.policy.usecases.policy.load;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Test;
import uk.co.idv.policy.entities.policy.DefaultPolicyRequest;
import uk.co.idv.policy.entities.policy.key.PolicyKey;
import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.policy.entities.policy.key.channelactivityalias.ChannelActivityAliasPolicyKeyMother;
import uk.co.idv.policy.entities.policy.key.channelactivity.ChannelActivityPolicyKeyMother;
import uk.co.idv.policy.entities.policy.key.channel.ChannelPolicyKeyMother;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.idv.policy.entities.policy.key.PolicyKeyConstants.ALL;

class PolicyKeyConverterTest {

    private final PolicyKeyConverter converter = new PolicyKeyConverter();

    @Test
    void shouldConvertChannelPolicyKeyToPolicyRequests() {
        PolicyKey key = ChannelPolicyKeyMother.build();

        Collection<PolicyRequest> requests = converter.toPolicyRequests(key);

        PolicyRequest expectedRequest = toExpectedChannelRequest(key);
        assertThat(requests).containsExactly(expectedRequest);
    }

    @Test
    void shouldConvertChannelActivityPolicyKeyToPolicyRequests() {
        PolicyKey key = ChannelActivityPolicyKeyMother.build();

        Collection<PolicyRequest> requests = converter.toPolicyRequests(key);

        PolicyRequest expectedRequest = toExpectedChannelActivityRequest(key);
        assertThat(requests).containsExactly(expectedRequest);
    }

    @Test
    void shouldConvertChannelActivityAliasPolicyKeyToPolicyRequests() {
        PolicyKey key = ChannelActivityAliasPolicyKeyMother.build();

        Collection<PolicyRequest> requests = converter.toPolicyRequests(key);

        PolicyRequest expectedRequest = toExpectedChannelActivityAliasRequest(key);
        assertThat(requests).containsExactly(expectedRequest);
    }

    private static PolicyRequest toExpectedChannelRequest(PolicyKey key) {
        return DefaultPolicyRequest.builder()
                .channelId(key.getChannelId())
                .activityName(ALL)
                .aliasTypes(Collections.singleton(ALL))
                .build();
    }

    private static PolicyRequest toExpectedChannelActivityRequest(PolicyKey key) {
        return DefaultPolicyRequest.builder()
                .channelId(key.getChannelId())
                .activityName(IterableUtils.first(key.getActivityNames()))
                .aliasTypes(Collections.singleton(ALL))
                .build();
    }

    private static PolicyRequest toExpectedChannelActivityAliasRequest(PolicyKey key) {
        return DefaultPolicyRequest.builder()
                .channelId(key.getChannelId())
                .activityName(IterableUtils.first(key.getActivityNames()))
                .aliasTypes(key.getAliasTypes())
                .build();
    }

}
