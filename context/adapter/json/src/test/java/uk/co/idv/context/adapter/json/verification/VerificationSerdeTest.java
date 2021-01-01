package uk.co.idv.context.adapter.json.verification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.entities.verification.Verification;
import uk.co.idv.context.entities.verification.VerificationMother;
import uk.co.idv.method.adapter.json.fake.FakeMethodMapping;
import uk.co.idv.method.adapter.json.method.MethodMapping;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class VerificationSerdeTest {

    private static final MethodMapping MAPPING = new FakeMethodMapping();
    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new VerificationModule(MAPPING));
    private static final Verification VERIFICATION = VerificationMother.incomplete();
    private static final String JSON = VerificationJsonMother.incomplete();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(VERIFICATION);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        Verification context = MAPPER.readValue(JSON, Verification.class);

        assertThat(context).isEqualTo(VERIFICATION);
    }

}
