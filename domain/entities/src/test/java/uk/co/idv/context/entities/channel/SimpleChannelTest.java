package uk.co.idv.context.entities.channel;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleChannelTest {

    private static final String ID = "my-channel";
    private static final CountryCode COUNTRY = CountryCode.GB;

    @Test
    void shouldReturnId() {
        Channel channel = SimpleChannelMother.withId(ID);

        assertThat(channel.getId()).isEqualTo(ID);
    }

    @Test
    void shouldReturnCountry() {
        Channel channel = SimpleChannelMother.withCountry(COUNTRY);

        assertThat(channel.getCountry()).isEqualTo(COUNTRY);
    }

    @Test
    void shouldReturnTrueIfIdMatches() {
        Channel channel = SimpleChannelMother.withId(ID);

        assertThat(channel.hasId(ID)).isTrue();
    }

    @Test
    void shouldReturnFalseIfIdDoesNotMatch() {
        Channel channel = SimpleChannelMother.withId(ID);

        assertThat(channel.hasId("other-id")).isFalse();
    }

}
