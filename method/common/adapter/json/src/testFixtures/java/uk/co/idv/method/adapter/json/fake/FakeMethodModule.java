package uk.co.idv.method.adapter.json.fake;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uk.co.idv.method.adapter.json.eligibility.EligibilityModule;
import uk.co.idv.method.adapter.json.method.MethodMixin;
import uk.co.idv.method.adapter.json.result.ResultModule;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.fake.FakeMethod;
import uk.co.idv.method.entities.method.fake.FakeMethodConfig;

import java.util.Arrays;

public class FakeMethodModule extends SimpleModule {

    public FakeMethodModule() {
        super("fake-method-module", Version.unknownVersion());

        setMixInAnnotation(Method.class, MethodMixin.class);

        addDeserializer(FakeMethod.class, new FakeMethodDeserializer());
        addDeserializer(FakeMethodConfig.class, new FakeMethodConfigDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new Jdk8Module(),
                new JavaTimeModule(),
                new EligibilityModule(),
                new ResultModule()
        );
    }

}
