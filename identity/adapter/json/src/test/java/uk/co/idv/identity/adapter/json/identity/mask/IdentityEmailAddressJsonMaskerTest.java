package uk.co.idv.identity.adapter.json.identity.mask;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.identity.adapter.json.identity.IdentityJsonMother;
import uk.co.idv.identity.adapter.json.phonenumber.PhoneNumberModule;
import uk.co.mruoc.json.mask.JsonMasker;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class IdentityEmailAddressJsonMaskerTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new PhoneNumberModule());

    private final JsonMasker masker = new IdentityEmailAddressJsonMasker(MAPPER);

    @Test
    void shouldMaskEmailAddressesInIdentityJson() {
        String json = IdentityJsonMother.example();

        String maskedJson = masker.apply(json);

        assertThatJson(maskedJson).isEqualTo(IdentityJsonMother.withMaskedEmailAddresses());
    }
}
