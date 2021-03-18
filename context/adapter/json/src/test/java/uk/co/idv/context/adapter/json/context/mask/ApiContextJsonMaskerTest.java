package uk.co.idv.context.adapter.json.context.mask;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.adapter.json.context.ApiContextJsonMother;
import uk.co.idv.context.adapter.json.context.create.CreateContextModule;

import java.util.function.UnaryOperator;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class ApiContextJsonMaskerTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new CreateContextModule());

    private final UnaryOperator<String> masker = new ApiContextJsonMasker(MAPPER);

    @Test
    void shouldProtectSensitiveDataInRequest() {
        String json = ApiContextJsonMother.build();

        String maskedJson = masker.apply(json);

        assertThatJson(maskedJson)
                .whenIgnoringPaths("$.request.policy.protectSensitiveData")
                .isEqualTo(ApiContextJsonMother.buildWithProtectedSensitiveData());
    }

}
