package uk.co.idv.context.entities.channel.gb;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.channel.Channel;

import static org.assertj.core.api.Assertions.assertThat;

class As3Test {

    @Test
    void shouldReturnId() {
        Channel channel = As3Mother.as3();

        assertThat(channel.getId()).isEqualTo("as3");
    }

    @Test
    void shouldReturnCountry() {
        Channel channel = As3Mother.as3();

        assertThat(channel.getCountry()).isEqualTo(CountryCode.GB);
    }

}
