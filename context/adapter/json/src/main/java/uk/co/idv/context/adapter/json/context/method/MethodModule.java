package uk.co.idv.context.adapter.json.context.method;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.context.method.otp.OtpModule;
import uk.co.idv.context.adapter.json.context.result.ResultModule;
import uk.co.idv.context.entities.context.method.Method;
import uk.co.idv.context.entities.context.method.Methods;

import java.util.Arrays;

public class MethodModule extends SimpleModule {

    public MethodModule() {
        super("method-module", Version.unknownVersion());

        setMixInAnnotation(Method.class, MethodMixin.class);
        setMixInAnnotation(Methods.class, MethodsMixin.class);

        addDeserializer(Method.class, new MethodDeserializer());
        addDeserializer(Methods.class, new MethodsDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new OtpModule(),
                new ResultModule()
        );
    }

}
