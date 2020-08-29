package uk.co.idv.context.adapter.json.phonenumber;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.entities.phonenumber.PhoneNumbersMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class PhoneNumbersSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new PhoneNumberModule())
            .setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private static final String JSON = PhoneNumbersJsonMother.build();
    private static final PhoneNumbers PHONE_NUMBERS = PhoneNumbersMother.two();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(PHONE_NUMBERS);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        PhoneNumbers phoneNumbers = MAPPER.readValue(JSON, PhoneNumbers.class);

        assertThat(phoneNumbers).isEqualTo(PHONE_NUMBERS);
    }

}
