package uk.co.idv.context.entities.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;

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
    void shouldReturnAliases() {
        Aliases aliases = AliasesMother.defaultAliasOnly();

        ExternalLockoutRequest request = DefaultExternalLockoutRequest.builder()
                .aliases(aliases)
                .build();

        assertThat(request.getAliases()).isEqualTo(aliases);
    }

    @Test
    void shouldReturnAliasTypes() {
        Aliases aliases = AliasesMother.defaultAliasOnly();

        ExternalLockoutRequest request = DefaultExternalLockoutRequest.builder()
                .aliases(aliases)
                .build();

        assertThat(request.getAliasTypes()).isEqualTo(aliases.getTypes());
    }

}
