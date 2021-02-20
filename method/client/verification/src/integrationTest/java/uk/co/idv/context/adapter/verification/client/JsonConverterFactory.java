package uk.co.idv.context.adapter.verification.client;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.common.adapter.json.error.ErrorModule;
import uk.co.idv.method.adapter.json.fake.FakeMethodMapping;
import uk.co.idv.method.adapter.json.method.MethodMappings;
import uk.co.idv.method.adapter.json.otp.OtpMapping;
import uk.co.idv.method.adapter.json.verification.VerificationModule;
import uk.co.mruoc.json.JsonConverter;
import uk.co.mruoc.json.jackson.JacksonJsonConverter;

public class JsonConverterFactory {

    public static JsonConverter build() {
        return build(buildMapper());
    }

    public static JsonConverter build(ObjectMapper mapper) {
        return new JacksonJsonConverter(mapper);
    }

    public static ObjectMapper buildMapper() {
        return ObjectMapperFactory.build(buildModules());
    }

    private static Module[] buildModules() {
        return new Module[] {
                new VerificationModule(new MethodMappings(new FakeMethodMapping(), new OtpMapping())),
                new ErrorModule()
        };
    }
}
