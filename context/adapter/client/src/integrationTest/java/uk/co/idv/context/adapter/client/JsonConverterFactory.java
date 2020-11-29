package uk.co.idv.context.adapter.client;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.common.adapter.json.error.ErrorModule;
import uk.co.idv.context.adapter.json.ContextParentModule;
import uk.co.idv.method.adapter.json.fake.FakeMethodMapping;
import uk.co.idv.method.adapter.json.method.MethodMappings;
import uk.co.idv.method.adapter.json.otp.OtpMapping;
import uk.co.mruoc.json.JsonConverter;
import uk.co.mruoc.json.jackson.JacksonJsonConverter;

public class JsonConverterFactory {

    public static JsonConverter build() {
        return build(
                new ContextParentModule(new MethodMappings(new FakeMethodMapping(), new OtpMapping())),
                new ErrorModule()
        );
    }

    private static JsonConverter build(Module... modules) {
        return new JacksonJsonConverter(buildMapper(modules));
    }

    private static ObjectMapper buildMapper(Module... modules) {
        return ObjectMapperFactory.build(modules);
    }

}
