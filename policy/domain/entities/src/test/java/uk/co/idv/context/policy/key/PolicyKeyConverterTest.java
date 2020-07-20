package uk.co.idv.context.policy.key;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.policy.PolicyKey;
import uk.co.idv.context.policy.PolicyRequest;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.idv.context.policy.key.CollectionUtils.getFirst;

class PolicyKeyConverterTest {

    private final PolicyKeyConverter converter = new PolicyKeyConverter();

    @Test
    void shouldConvertChannelPolicyKeyToPolicyRequests() {
        PolicyKey key = ChannelPolicyKeyMother.defaultChannelKey();

        Collection<PolicyRequest> requests = converter.toPolicyRequests(key);

        PolicyRequest expectedRequest = toExpectedChannelRequest(key);
        assertThat(requests).containsExactly(expectedRequest);
    }

    @Test
    void shouldConvertChannelActivityPolicyKeyToPolicyRequests() {
        PolicyKey key = ChannelActivityPolicyKeyMother.defaultChannelActivityKey();

        Collection<PolicyRequest> requests = converter.toPolicyRequests(key);

        PolicyRequest expectedRequest = toExpectedChannelActivityRequest(key);
        assertThat(requests).containsExactly(expectedRequest);
    }

    @Test
    void shouldConvertChannelActivityAliasPolicyKeyToPolicyRequests() {
        PolicyKey key = ChannelActivityAliasPolicyKeyMother.defaultChannelActivityAliasKey();

        Collection<PolicyRequest> requests = converter.toPolicyRequests(key);

        PolicyRequest expectedRequest = toExpectedChannelActivityAliasRequest(key);
        assertThat(requests).containsExactly(expectedRequest);
    }

    private static PolicyRequest toExpectedChannelRequest(PolicyKey key) {
        return PolicyRequest.builder()
                .channelId(key.getChannelId())
                .activityName(PolicyKey.ALL)
                .aliasType(PolicyKey.ALL)
                .build();
    }

    private static PolicyRequest toExpectedChannelActivityRequest(PolicyKey key) {
        return PolicyRequest.builder()
                .channelId(key.getChannelId())
                .activityName(getFirst(key.getActivityNames()))
                .aliasType(PolicyKey.ALL)
                .build();
    }

    private static PolicyRequest toExpectedChannelActivityAliasRequest(PolicyKey key) {
        return PolicyRequest.builder()
                .channelId(key.getChannelId())
                .activityName(getFirst(key.getActivityNames()))
                .aliasType(getFirst(key.getAliasTypes()))
                .build();
    }

}
