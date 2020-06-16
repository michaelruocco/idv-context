package uk.co.idv.context.usecases.identity.find.external;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;
import uk.co.idv.context.usecases.identity.find.FindIdentityRequest;
import uk.co.idv.context.usecases.identity.request.ExternalFindIdentityRequestMother;

import static org.assertj.core.api.Assertions.assertThat;

class ExternalFindIdentityRequestTest {

    @Test
    void shouldReturnAliases() {
        Aliases aliases = AliasesMother.idvIdAndDebitCardNumber();

        FindIdentityRequest request = ExternalFindIdentityRequestMother.withAliases(aliases);

        assertThat(request.getAliases()).isEqualTo(aliases);
    }

    @Test
    void shouldReturnChannel() {
        Channel channel = DefaultChannelMother.build();

        ExternalFindIdentityRequest request = ExternalFindIdentityRequestMother.withChannel(channel);

        assertThat(request.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldReturnChannelIdFromChannel() {
        Channel channel = DefaultChannelMother.build();

        ExternalFindIdentityRequest request = ExternalFindIdentityRequestMother.withChannel(channel);

        assertThat(request.getChannelId()).isEqualTo(channel.getId());
    }

    @Test
    void shouldReturnCountryFromChannel() {
        Channel channel = DefaultChannelMother.build();

        ExternalFindIdentityRequest request = ExternalFindIdentityRequestMother.withChannel(channel);

        assertThat(request.getCountry()).isEqualTo(channel.getCountry());
    }

}
