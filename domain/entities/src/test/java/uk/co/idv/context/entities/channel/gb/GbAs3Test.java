package uk.co.idv.context.entities.channel.gb;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.channel.Channel;

import static org.assertj.core.api.Assertions.assertThat;

class GbAs3Test {

    @Test
    void shouldReturnId() {
        Channel channel = GbAs3Mother.as3();

        assertThat(channel.getId()).isEqualTo("as3");
    }

    @Test
    void shouldReturnCountry() {
        Channel channel = GbAs3Mother.as3();

        assertThat(channel.getCountry()).isEqualTo(CountryCode.GB);
    }

}
