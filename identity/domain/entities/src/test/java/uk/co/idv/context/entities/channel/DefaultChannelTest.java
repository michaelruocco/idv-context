package uk.co.idv.context.entities.channel;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultChannelTest {

    private static final String ID = "my-channel";
    @Test
    void shouldReturnId() {
        Channel channel = DefaultChannelMother.withId(ID);

        assertThat(channel.getId()).isEqualTo(ID);
    }

    @Test
    void shouldReturnCountry() {
        CountryCode country = CountryCode.GB;

        Channel channel = DefaultChannelMother.withCountry(country);

        assertThat(channel.getCountry()).isEqualTo(country);
    }

    @Test
    void shouldReturnTrueIfIdMatches() {
        Channel channel = DefaultChannelMother.withId(ID);

        assertThat(channel.hasId(ID)).isTrue();
    }

    @Test
    void shouldReturnFalseIfIdDoesNotMatch() {
        Channel channel = DefaultChannelMother.withId(ID);

        assertThat(channel.hasId("other-id")).isFalse();
    }

}
