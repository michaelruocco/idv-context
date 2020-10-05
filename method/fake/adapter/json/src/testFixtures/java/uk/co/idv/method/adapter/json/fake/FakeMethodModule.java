package uk.co.idv.method.adapter.json.fake;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.method.entities.fake.FakeMethod;
import uk.co.idv.method.entities.fake.FakeMethodConfig;

public class FakeMethodModule extends SimpleModule {

    public FakeMethodModule() {
        super("fake-method-module", Version.unknownVersion());

        addDeserializer(FakeMethod.class, new FakeMethodDeserializer());
        addDeserializer(FakeMethodConfig.class, new FakeMethodConfigDeserializer());
    }

}
