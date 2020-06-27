package uk.co.idv.context.usecases.eligibility;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;
import uk.co.idv.context.usecases.eligibility.external.ExternalFindIdentityRequest;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class CreateEligibilityRequestTest {

    @Test
    void shouldReturnAliases() {
        Aliases aliases = AliasesMother.idvIdAndDebitCardNumber();

        ExternalFindIdentityRequest request = CreateEligibilityRequestMother.withAliases(aliases);

        assertThat(request.getAliases()).isEqualTo(aliases);
    }

    @Test
    void shouldReturnChannel() {
        Channel channel = DefaultChannelMother.build();

        ExternalFindIdentityRequest request = CreateEligibilityRequestMother.withChannel(channel);

        assertThat(request.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldReturnChannelIdFromChannel() {
        Channel channel = DefaultChannelMother.build();

        ExternalFindIdentityRequest request = CreateEligibilityRequestMother.withChannel(channel);

        assertThat(request.getChannelId()).isEqualTo(channel.getId());
    }

    @Test
    void shouldReturnCountryFromChannel() {
        Channel channel = DefaultChannelMother.build();

        ExternalFindIdentityRequest request = CreateEligibilityRequestMother.withChannel(channel);

        assertThat(request.getCountry()).isEqualTo(channel.getCountry());
    }

    @Test
    void shouldReturnRequested() {
        Collection<String> requested = CreateEligibilityRequestMother.allRequested();

        ExternalFindIdentityRequest request = CreateEligibilityRequestMother.withRequested(requested);

        assertThat(request.getRequested()).isEqualTo(requested);
    }

    @Test
    void shouldReturnEmailAddressesRequestedTrueIfRequested() {
        Collection<String> requested = Collections.singleton("email-addresses");

        ExternalFindIdentityRequest request = CreateEligibilityRequestMother.withRequested(requested);

        assertThat(request.emailAddressesRequested()).isTrue();
    }

    @Test
    void shouldReturnEmailAddressesRequestedFalseIfNotRequested() {
        Collection<String> requested = Collections.emptyList();

        ExternalFindIdentityRequest request = CreateEligibilityRequestMother.withRequested(requested);

        assertThat(request.emailAddressesRequested()).isFalse();
    }

    @Test
    void shouldReturnPhoneNumbersRequestedTrueIfRequested() {
        Collection<String> requested = Collections.singleton("phone-numbers");

        ExternalFindIdentityRequest request = CreateEligibilityRequestMother.withRequested(requested);

        assertThat(request.phoneNumbersRequested()).isTrue();
    }

    @Test
    void shouldReturnPhoneNumbersRequestedFalseIfNotRequested() {
        Collection<String> requested = Collections.emptyList();

        ExternalFindIdentityRequest request = CreateEligibilityRequestMother.withRequested(requested);

        assertThat(request.phoneNumbersRequested()).isFalse();
    }

}
