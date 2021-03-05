package uk.co.idv.identity.entities.channel.gb;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.channel.Channel;

import static org.assertj.core.api.Assertions.assertThat;

class AbcTest {

    @Test
    void shouldReturnId() {
        Channel channel = AbcMother.abc();

        assertThat(channel.getId()).isEqualTo("abc");
    }

    @Test
    void shouldReturnCountry() {
        Channel channel = AbcMother.abc();

        assertThat(channel.getCountry()).isEqualTo(CountryCode.GB);
    }

    @Test
    void shouldReturnValidCookie() {
        Channel channel = AbcMother.abc();

        assertThat(channel.hasValidCookie()).contains(true);
    }

}
