package uk.co.idv.policy.entities.policy;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultPolicyRequestTest {

    @Test
    void shouldReturnChannelId() {
        String channelId = "my-channel";

        PolicyRequest request = DefaultPolicyRequest.builder()
                .channelId(channelId)
                .build();

        assertThat(request.getChannelId()).isEqualTo(channelId);
    }

    @Test
    void shouldReturnActivityName() {
        String activityName = "my-activity";

        PolicyRequest request = DefaultPolicyRequest.builder()
                .activityName(activityName)
                .build();

        assertThat(request.getActivityName()).isEqualTo(activityName);
    }

    @Test
    void shouldReturnAliasTypes() {
        Collection<String> aliasTypes = Collections.singleton("my-alias");

        PolicyRequest request = DefaultPolicyRequest.builder()
                .aliasTypes(aliasTypes)
                .build();

        assertThat(request.getAliasTypes()).isEqualTo(aliasTypes);
    }

}
