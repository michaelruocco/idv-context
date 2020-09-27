package uk.co.idv.identity.entities.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.channel.DefaultChannelMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class FindIdentityRequestTest {

    @Test
    void shouldReturnChannelIdFromChannel() {
        Channel channel = DefaultChannelMother.build();

        FindIdentityRequest request = FakeFindIdentityRequest.builder()
                .channel(channel)
                .build();

        assertThat(request.getChannelId()).isEqualTo(channel.getId());
    }

    @Test
    void shouldReturnCountryFromChannel() {
        Channel channel = DefaultChannelMother.build();

        FindIdentityRequest request = FakeFindIdentityRequest.builder()
                .channel(channel)
                .build();

        assertThat(request.getCountry()).isEqualTo(channel.getCountry());
    }

    @Test
    void shouldReturnEmailAddressesRequestedFromRequestedData() {
        RequestedData requestedData = mock(RequestedData.class);
        given(requestedData.emailAddressesRequested()).willReturn(true);

        FindIdentityRequest request = FakeFindIdentityRequest.builder()
                .requestedData(requestedData)
                .build();

        assertThat(request.emailAddressesRequested()).isTrue();
    }

    @Test
    void shouldReturnPhoneNumbersRequestedFromRequestedData() {
        RequestedData requestedData = mock(RequestedData.class);
        given(requestedData.phoneNumbersRequested()).willReturn(true);

        FindIdentityRequest request = FakeFindIdentityRequest.builder()
                .requestedData(requestedData)
                .build();

        assertThat(request.phoneNumbersRequested()).isTrue();
    }

}
