package uk.co.idv.identity.adapter.json.phonenumber.mask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.identity.adapter.json.phonenumber.PhoneNumberModule;
import uk.co.idv.identity.adapter.json.phonenumber.PhoneNumbersJsonMother;
import uk.co.mruoc.json.mask.JsonMasker;
import uk.co.mruoc.json.mask.JsonPathFactory;

import java.util.Collection;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class PhoneNumberJsonMaskerTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new PhoneNumberModule());

    private final Collection<JsonPath> jsonPaths = JsonPathFactory.toJsonPaths("$.[*].value");
    private final JsonMasker masker = new PhoneNumberJsonMasker(MAPPER, jsonPaths);

    @Test
    void shouldMaskValuesAtJsonPathAsPhoneNumbers() {
        String json = PhoneNumbersJsonMother.build();

        String maskedJson = masker.apply(json);

        assertThatJson(maskedJson).isEqualTo(PhoneNumbersJsonMother.masked());
    }

}
