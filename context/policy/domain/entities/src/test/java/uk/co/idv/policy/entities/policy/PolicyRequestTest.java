package uk.co.idv.policy.entities.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.policy.entities.policy.DefaultPolicyRequest.DefaultPolicyRequestBuilder;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class PolicyRequestTest {

    private final DefaultPolicyRequestBuilder builder = DefaultPolicyRequest.builder();

    @Test
    void shouldReturnChannelId() {
        String channelId = "channel-id";

        PolicyRequest request = builder.channelId(channelId).build();

        assertThat(request.getChannelId()).isEqualTo(channelId);
    }

    @Test
    void shouldReturnActivityName() {
        String activityName = "activity-name";

        PolicyRequest request = builder.activityName(activityName).build();

        assertThat(request.getActivityName()).isEqualTo(activityName);
    }

    @Test
    void shouldReturnAliasTypes() {
        Collection<String> aliasTypes = Collections.singleton("alias-type");

        PolicyRequest request = builder.aliasTypes(aliasTypes).build();

        assertThat(request.getAliasTypes()).isEqualTo(aliasTypes);
    }

}
