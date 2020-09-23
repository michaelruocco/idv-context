package uk.co.idv.context.adapter.json.context.method;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.common.adapter.json.duration.DurationModule;
import uk.co.idv.context.adapter.json.context.method.otp.OtpModule;
import uk.co.idv.context.entities.context.method.Method;

import java.util.Arrays;

public class MethodModule extends SimpleModule {

    public MethodModule() {
        super("method-module", Version.unknownVersion());

        setMixInAnnotation(Method.class, MethodMixin.class);

        addDeserializer(Method.class, new MethodDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new OtpModule(),
                new DurationModule()
        );
    }

}
