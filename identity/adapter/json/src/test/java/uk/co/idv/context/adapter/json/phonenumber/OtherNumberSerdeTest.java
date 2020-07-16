package uk.co.idv.context.adapter.json.phonenumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.phonenumber.OtherPhoneNumberMother;
import uk.co.idv.context.entities.phonenumber.PhoneNumber;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

public class OtherNumberSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new PhoneNumberModule());
    private static final String JSON = OtherPhoneNumberJsonMother.other();
    private static final PhoneNumber NUMBER = OtherPhoneNumberMother.other();

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
