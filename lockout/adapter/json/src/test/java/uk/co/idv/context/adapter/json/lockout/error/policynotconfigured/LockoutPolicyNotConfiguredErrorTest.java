package uk.co.idv.context.adapter.json.lockout.error.policynotconfigured;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.entities.policy.PolicyRequest;
import uk.co.idv.context.entities.policy.PolicyRequestMother;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;

class LockoutPolicyNotConfiguredErrorTest {

    private final PolicyRequest request = PolicyRequestMother.build();

    private final ApiError error = new LockoutPolicyNotConfiguredError(request);

    @Test
    void shouldReturnStatus() {
        assertThat(error.getStatus()).isEqualTo(422);
    }

    @Test
    void shouldReturnTitle() {
        assertThat(error.getTitle()).isEqualTo("Lockout policy not configured");
    }

    @Test
    void shouldReturnMessage() {
        assertThat(error.getMessage()).isEqualTo("Lockout policy not configured for channel, activity and alias combination");
    }

    @Test
    void shouldReturnMeta() {
        assertThat(error.getMeta()).contains(
                entry("channelId", request.getChannelId()),
                entry("activityName", request.getActivityName()),
                entry("aliasType", request.getAliasType())
        );
    }

}
