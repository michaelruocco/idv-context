package uk.co.idv.context.adapter.json.phonenumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.phonenumber.PhoneNumberMother;
import uk.co.idv.context.entities.phonenumber.PhoneNumber;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class PhoneNumberSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new PhoneNumberModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private static final String JSON = PhoneNumberJsonMother.build();
    private static final PhoneNumber NUMBER = PhoneNumberMother.example();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(NUMBER);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        PhoneNumber number = MAPPER.readValue(JSON, PhoneNumber.class);

        assertThat(number).isEqualTo(NUMBER);
    }

}
