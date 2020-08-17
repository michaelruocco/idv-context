package uk.co.idv.context.entities.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.DefaultAliasMother;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultExternalLockoutRequestTest {

    @Test
    void shouldReturnChannelId() {
        String channelId = "my-channel";

        ExternalLockoutRequest request = DefaultExternalLockoutRequest.builder()
                .channelId(channelId)
                .build();

        assertThat(request.getChannelId()).isEqualTo(channelId);
    }

    @Test
    void shouldReturnActivityName() {
        String activityName = "my-activity";

        ExternalLockoutRequest request = DefaultExternalLockoutRequest.builder()
                .activityName(activityName)
                .build();

        assertThat(request.getActivityName()).isEqualTo(activityName);
    }

    @Test
    void shouldReturnAlias() {
        Alias alias = DefaultAliasMother.build();

        ExternalLockoutRequest request = DefaultExternalLockoutRequest.builder()
                .alias(alias)
                .build();

        assertThat(request.getAlias()).isEqualTo(alias);
    }

    @Test
    void shouldReturnAliasType() {
        Alias alias = DefaultAliasMother.build();

        ExternalLockoutRequest request = DefaultExternalLockoutRequest.builder()
                .alias(alias)
                .build();

        assertThat(request.getAliasType()).isEqualTo(alias.getType());
    }

}
