package uk.co.idv.identity.adapter.json.emailaddress;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class EmailAddressesSerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new EmailAddressModule());
    private static final String JSON = EmailAddressesJsonMother.two();
    private static final EmailAddresses EMAIL_ADDRESSES = EmailAddressesMother.two();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(EMAIL_ADDRESSES);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        EmailAddresses emailAddresses = MAPPER.readValue(JSON, EmailAddresses.class);

        assertThat(emailAddresses).isEqualTo(EMAIL_ADDRESSES);
    }

}
