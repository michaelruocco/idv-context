package uk.co.idv.identity.adapter.json.emailaddress.mask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.identity.adapter.json.emailaddress.EmailAddressModule;
import uk.co.idv.identity.adapter.json.emailaddress.EmailAddressesJsonMother;
import uk.co.mruoc.json.mask.JsonMasker;
import uk.co.mruoc.json.mask.JsonPathFactory;

import java.util.Collection;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class EmailAddressJsonMaskerTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new EmailAddressModule());

    private final Collection<JsonPath> jsonPaths = JsonPathFactory.toJsonPaths("$.[*]");
    private final JsonMasker masker = new EmailAddressJsonMasker(MAPPER, jsonPaths);

    @Test
    void shouldMaskValuesAtJsonPathAsEmailAddress() {
        String json = EmailAddressesJsonMother.two();

        String maskedJson = masker.apply(json);

        assertThatJson(maskedJson).isEqualTo(EmailAddressesJsonMother.twoMasked());
    }

}
