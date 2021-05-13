package uk.co.idv.identity.entities.channel.gb;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.channel.Rsa;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class GbRsaTest {

    @Test
    void shouldReturnId() {
        Channel rsa = GbRsaMother.rsa();

        assertThat(rsa.getId()).isEqualTo("gb-rsa");
    }

    @Test
    void shouldReturnCountry() {
        Channel rsa = GbRsaMother.rsa();

        assertThat(rsa.getCountry()).isEqualTo(CountryCode.GB);
    }

    @Test
    void shouldReturnIssuerSessionIdIfSet() {
        UUID id = UUID.randomUUID();

        Rsa rsa = GbRsaMother.withIssuerSessionId(id);

        assertThat(rsa.getIssuerSessionId()).contains(id);
    }

    @Test
    void shouldReturnEmptyIssuerSessionIdIfNotSet() {
        Rsa rsa = GbRsaMother.withoutIssuerSessionId();

        assertThat(rsa.getIssuerSessionId()).isEmpty();
    }

    @Test
    void dsSessionIdShouldAlwaysByEmpty() {
        Rsa rsa = GbRsaMother.rsa();

        assertThat(rsa.getDsSessionId()).isEmpty();
    }

    @Test
    void validCookieShouldAlwaysBeEmpty() {
        Channel rsa = GbRsaMother.rsa();

        assertThat(rsa.hasValidCookie()).isEmpty();
    }

}
