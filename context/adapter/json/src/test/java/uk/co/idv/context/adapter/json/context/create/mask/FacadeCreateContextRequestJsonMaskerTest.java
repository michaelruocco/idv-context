package uk.co.idv.context.adapter.json.context.create.mask;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.adapter.json.context.ContextModule;
import uk.co.idv.context.adapter.json.context.create.FacadeCreateContextRequestJsonMother;
import uk.co.idv.method.adapter.json.fake.FakeMethodMapping;
import uk.co.idv.method.adapter.json.method.MethodMapping;

import java.util.function.UnaryOperator;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class FacadeCreateContextRequestJsonMaskerTest {

    private static final MethodMapping MAPPING = new FakeMethodMapping();
    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new ContextModule(MAPPING));

    private final UnaryOperator<String> masker = new FacadeCreateContextRequestJsonMasker(MAPPER);

    @Test
    void shouldMaskSensitiveDataInRequest() {
        String json = FacadeCreateContextRequestJsonMother.withChannelData();

        String maskedJson = masker.apply(json);

        assertThatJson(maskedJson).isEqualTo(FacadeCreateContextRequestJsonMother.withMaskedSensitiveData());
    }

}
