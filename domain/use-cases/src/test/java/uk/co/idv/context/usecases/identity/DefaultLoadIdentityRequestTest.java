package uk.co.idv.context.usecases.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.alias.DefaultAliasMother;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultLoadIdentityRequestTest {

    @Test
    void shouldReturnEmptyAliasesIfNotSet() {
        LoadIdentityRequest request = DefaultLoadIdentityRequest.builder().build();

        assertThat(request.getAliases()).isEmpty();
    }

    @Test
    void shouldReturnProvidedAlias() {
        Alias alias = DefaultAliasMother.build();

        LoadIdentityRequest request = LoadIdentityRequestMother.withProvidedAlias(alias);

        assertThat(request.getProvidedAlias()).isEqualTo(alias);
    }

    @Test
    void shouldReturnChannel() {
        Channel channel = DefaultChannelMother.build();

        LoadIdentityRequest request = LoadIdentityRequestMother.withChannel(channel);

        assertThat(request.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldReturnCountryFromChannel() {
        Channel channel = DefaultChannelMother.build();

        LoadIdentityRequest request = LoadIdentityRequestMother.withChannel(channel);

        assertThat(request.getCountry()).isEqualTo(channel.getCountry());
    }

    @Test
    void shouldCopyAllAttributesWhenSettingAliases() {
        LoadIdentityRequest request = LoadIdentityRequestMother.build();
        Aliases aliases = AliasesMother.idvIdAndCreditCardNumber();

        LoadIdentityRequest updatedRequest = request.setAliases(aliases);

        assertThat(updatedRequest).isEqualToIgnoringGivenFields(request, "aliases");
        assertThat(updatedRequest.getAliases()).isEqualTo(aliases);
    }

}
