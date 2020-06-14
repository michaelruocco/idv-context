package uk.co.idv.context.usecases.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultUpsertIdentityRequestTest {

    @Test
    void shouldReturnIdentity() {
        Identity identity = IdentityMother.example();

        UpsertIdentityRequest request = UpsertIdentityRequestMother.withIdentity(identity);

        assertThat(request.getIdentity()).isEqualTo(identity);
    }

    @Test
    void shouldReturnAliasesFromIdentity() {
        Identity identity = IdentityMother.example();

        UpsertIdentityRequest request = UpsertIdentityRequestMother.withIdentity(identity);

        assertThat(request.getAliases()).isEqualTo(identity.getAliases());
    }

    @Test
    void shouldReturnChannel() {
        Channel channel = DefaultChannelMother.build();

        UpsertIdentityRequest request = UpsertIdentityRequestMother.withChannel(channel);

        assertThat(request.getChannel()).isEqualTo(channel);
    }

}
