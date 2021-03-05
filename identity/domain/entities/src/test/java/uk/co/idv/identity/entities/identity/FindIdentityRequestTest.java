package uk.co.idv.identity.entities.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.channel.DefaultChannelMother;

import static org.assertj.core.api.Assertions.assertThat;

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

}
