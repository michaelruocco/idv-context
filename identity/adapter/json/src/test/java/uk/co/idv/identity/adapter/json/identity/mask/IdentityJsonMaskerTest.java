package uk.co.idv.identity.adapter.json.identity.mask;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.identity.adapter.json.identity.IdentityJsonMother;
import uk.co.idv.identity.adapter.json.phonenumber.PhoneNumberModule;

import java.util.function.UnaryOperator;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class IdentityJsonMaskerTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new PhoneNumberModule());

    private final UnaryOperator<String> masker = new IdentityJsonMasker(MAPPER);

    @Test
    void shouldMaskSensitiveDataInIdentityJson() {
        String json = IdentityJsonMother.example();

        String maskedJson = masker.apply(json);

        assertThatJson(maskedJson).isEqualTo(IdentityJsonMother.withMaskedSensitiveData());
    }

}
