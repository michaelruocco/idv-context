package uk.co.idv.context.adapter.json.context.method.fake;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.method.entities.method.FakeMethod;
import uk.co.idv.method.entities.policy.FakeMethodConfig;

public class FakeMethodModule extends SimpleModule {

    public FakeMethodModule() {
        super("otp-module", Version.unknownVersion());

        addDeserializer(FakeMethod.class, new FakeMethodDeserializer());
        addDeserializer(FakeMethodConfig.class, new FakeMethodConfigDeserializer());
    }

}
