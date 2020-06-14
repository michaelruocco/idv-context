package uk.co.idv.context.usecases.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultFindIdentityRequestTest {

    @Test
    void shouldReturnAliases() {
        Aliases aliases = AliasesMother.idvIdAndDebitCardNumber();

        FindIdentityRequest request = FindIdentityRequestMother.withAliases(aliases);

        assertThat(request.getAliases()).isEqualTo(aliases);
    }

    @Test
    void shouldReturnChannel() {
        Channel channel = DefaultChannelMother.build();

        FindIdentityRequest request = FindIdentityRequestMother.withChannel(channel);

        assertThat(request.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldReturnChannelIdFromChannel() {
        Channel channel = DefaultChannelMother.build();

        FindIdentityRequest request = FindIdentityRequestMother.withChannel(channel);

        assertThat(request.getChannelId()).isEqualTo(channel.getId());
    }

    @Test
    void shouldReturnCountryFromChannel() {
        Channel channel = DefaultChannelMother.build();

        FindIdentityRequest request = FindIdentityRequestMother.withChannel(channel);

        assertThat(request.getCountry()).isEqualTo(channel.getCountry());
    }

}
