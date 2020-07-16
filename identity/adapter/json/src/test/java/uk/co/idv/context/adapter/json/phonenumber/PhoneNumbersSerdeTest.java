package uk.co.idv.context.adapter.json.phonenumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.entities.phonenumber.PhoneNumbersMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class PhoneNumbersSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new PhoneNumberModule());
    private static final String JSON = PhoneNumbersJsonMother.mobileAndOther();
    private static final PhoneNumbers PHONE_NUMBERS = PhoneNumbersMother.mobileAndOther();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        final String json = MAPPER.writeValueAsString(PHONE_NUMBERS);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        final PhoneNumbers phoneNumbers = MAPPER.readValue(JSON, PhoneNumbers.class);

        assertThat(phoneNumbers).isEqualTo(PHONE_NUMBERS);
    }

}
