package uk.co.idv.context.usecases.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.alias.DefaultAliasMother;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultFindIdentityRequestTest {

    @Test
    void shouldReturnEmptyAliasesIfNotSet() {
        FindIdentityRequest request = DefaultFindIdentityRequest.builder().build();

        assertThat(request.getAliases()).isEmpty();
    }

    @Test
    void shouldReturnProvidedAlias() {
        Alias alias = DefaultAliasMother.build();

        FindIdentityRequest request = FindIdentityRequestMother.withProvidedAlias(alias);

        assertThat(request.getProvidedAlias()).isEqualTo(alias);
    }

    @Test
    void shouldReturnChannel() {
        Channel channel = DefaultChannelMother.build();

        FindIdentityRequest request = FindIdentityRequestMother.withChannel(channel);

        assertThat(request.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldReturnCountryFromChannel() {
        Channel channel = DefaultChannelMother.build();

        FindIdentityRequest request = FindIdentityRequestMother.withChannel(channel);

        assertThat(request.getCountry()).isEqualTo(channel.getCountry());
    }

    @Test
    void shouldCopyAllOtherAttributesWhenSettingAliases() {
        FindIdentityRequest request = FindIdentityRequestMother.build();
        Aliases aliases = AliasesMother.idvIdAndCreditCardNumber();

        FindIdentityRequest updatedRequest = request.setAliases(aliases);

        assertThat(updatedRequest).isEqualToIgnoringGivenFields(request, "aliases");
        assertThat(updatedRequest.getAliases()).isEqualTo(aliases);
    }

}
