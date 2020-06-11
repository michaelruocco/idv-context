package uk.co.idv.context.entities.channel.de;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.Rsa;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class DeRsaTest {

    @Test
    void shouldReturnId() {
        Channel rsa = DeRsaMother.rsa();

        assertThat(rsa.getId()).isEqualTo("rsa");
    }

    @Test
    void shouldReturnCountry() {
        Channel rsa = DeRsaMother.rsa();

        assertThat(rsa.getCountry()).isEqualTo(CountryCode.DE);
    }

    @Test
    void shouldReturnIssuerSessionIdIfSet() {
        UUID id = UUID.randomUUID();

        Rsa rsa = DeRsaMother.withIssuerSessionId(id);

        assertThat(rsa.getIssuerSessionId()).contains(id);
    }

    @Test
    void shouldReturnEmptyIssuerSessionIdIfNotSet() {
        Rsa rsa = DeRsaMother.withoutIssuerSessionId();

        assertThat(rsa.getIssuerSessionId()).isEmpty();
    }

    @Test
    void shouldReturnDsSessionIdIfSet() {
        UUID id = UUID.randomUUID();

        Rsa rsa = DeRsaMother.withDsSessionId(id);

        assertThat(rsa.getDsSessionId()).contains(id);
    }

    @Test
    void shouldReturnEmptyDsSessionIdIfNotSet() {
        Rsa rsa = DeRsaMother.withoutDsSessionId();

        assertThat(rsa.getDsSessionId()).isEmpty();
    }

}
