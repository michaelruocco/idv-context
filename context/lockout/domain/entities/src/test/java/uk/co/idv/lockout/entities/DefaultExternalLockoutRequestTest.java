package uk.co.idv.lockout.entities;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.AliasesMother;
import uk.co.idv.identity.entities.alias.DefaultAliases;

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
        DefaultAliases aliases = AliasesMother.defaultAliasOnly();

        ExternalLockoutRequest request = DefaultExternalLockoutRequest.builder()
                .aliases(aliases)
                .build();

        assertThat(request.getAliases()).isEqualTo(aliases);
    }

    @Test
    void shouldReturnAliasTypes() {
        DefaultAliases aliases = AliasesMother.defaultAliasOnly();

        ExternalLockoutRequest request = DefaultExternalLockoutRequest.builder()
                .aliases(aliases)
                .build();

        assertThat(request.getAliasTypes()).isEqualTo(aliases.getTypes());
    }

}
