package uk.co.idv.method.adapter.json.fake.policy;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uk.co.idv.method.adapter.json.fake.FakeMethodConfigDeserializer;
import uk.co.idv.method.adapter.json.policy.MethodPolicyMixin;
import uk.co.idv.method.entities.method.fake.FakeMethodConfig;
import uk.co.idv.method.entities.method.fake.policy.FakeMethodPolicy;

import java.util.Collections;

public class FakeMethodPolicyModule extends SimpleModule {

    public FakeMethodPolicyModule() {
        super("fake-method-policy-module", Version.unknownVersion());

        setMixInAnnotation(FakeMethodPolicy.class, MethodPolicyMixin.class);

        addDeserializer(FakeMethodPolicy.class, new FakeMethodPolicyDeserializer());
        addDeserializer(FakeMethodConfig.class, new FakeMethodConfigDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new JavaTimeModule());
    }

}
