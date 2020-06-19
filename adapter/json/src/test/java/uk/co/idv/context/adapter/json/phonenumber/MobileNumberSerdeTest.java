package uk.co.idv.context.adapter.json.phonenumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.phonenumber.MobilePhoneNumberMother;
import uk.co.idv.context.entities.phonenumber.PhoneNumber;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

public class MobileNumberSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new PhoneNumberModule());
    private static final String JSON = ContentLoader.loadContentFromClasspath("phonenumber/mobile-number.json");
    private static final PhoneNumber NUMBER = MobilePhoneNumberMother.mobile();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(NUMBER);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        final PhoneNumber number = MAPPER.readValue(JSON, PhoneNumber.class);

        assertThat(number).isEqualTo(NUMBER);
    }

}
