package uk.co.idv.context.usecases.identity.request;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.UpdateIdentityRequest;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultUpdateIdentityRequestTest {

    @Test
    void shouldReturnIdentity() {
        Identity identity = IdentityMother.example();

        UpdateIdentityRequest request = UpdateIdentityRequestMother.withIdentity(identity);

        assertThat(request.getIdentity()).isEqualTo(identity);
    }

    @Test
    void shouldReturnAliasesFromIdentity() {
        Identity identity = IdentityMother.example();

        UpdateIdentityRequest request = UpdateIdentityRequestMother.withIdentity(identity);

        assertThat(request.getAliases()).isEqualTo(identity.getAliases());
    }

    @Test
    void shouldReturnChannel() {
        Channel channel = DefaultChannelMother.build();

        UpdateIdentityRequest request = UpdateIdentityRequestMother.withChannel(channel);

        assertThat(request.getChannel()).isEqualTo(channel);
    }

}
