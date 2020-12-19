package uk.co.idv.context.adapter.json.context.mask;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.adapter.json.context.ContextJsonMother;
import uk.co.idv.context.adapter.json.context.create.CreateContextModule;

import java.util.function.UnaryOperator;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class ContextJsonMaskerTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new CreateContextModule());

    private final UnaryOperator<String> masker = new ContextJsonMasker(MAPPER);

    @Test
    void shouldProtectSensitiveDataInRequest() {
        String json = ContextJsonMother.build();

        String maskedJson = masker.apply(json);

        assertThatJson(maskedJson)
                .whenIgnoringPaths("$.request.policy.protectSensitiveData")
                .isEqualTo(ContextJsonMother.buildWithProtectedSensitiveData());
    }

}
