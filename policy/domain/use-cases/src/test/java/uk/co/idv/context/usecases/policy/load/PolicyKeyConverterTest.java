package uk.co.idv.context.usecases.policy.load;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.DefaultPolicyRequest;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.PolicyRequest;
import uk.co.idv.context.entities.policy.key.ChannelActivityAliasPolicyKeyMother;
import uk.co.idv.context.entities.policy.key.ChannelActivityPolicyKeyMother;
import uk.co.idv.context.entities.policy.key.ChannelPolicyKeyMother;
import uk.co.idv.context.entities.policy.key.CollectionUtils;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.idv.context.entities.policy.key.PolicyKeyConstants.ALL;

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
                .activityName(CollectionUtils.getFirst(key.getActivityNames()))
                .aliasTypes(Collections.singleton(ALL))
                .build();
    }

    private static PolicyRequest toExpectedChannelActivityAliasRequest(PolicyKey key) {
        return DefaultPolicyRequest.builder()
                .channelId(key.getChannelId())
                .activityName(CollectionUtils.getFirst(key.getActivityNames()))
                .aliasTypes(key.getAliasTypes())
                .build();
    }

}
