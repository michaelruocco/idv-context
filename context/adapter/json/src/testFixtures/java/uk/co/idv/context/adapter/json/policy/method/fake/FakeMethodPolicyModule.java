package uk.co.idv.context.adapter.json.policy.method.fake;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.method.entities.policy.FakeMethodPolicy;

public class FakeMethodPolicyModule extends SimpleModule {

    public FakeMethodPolicyModule() {
        super("fake-method-policy-module", Version.unknownVersion());

        addDeserializer(FakeMethodPolicy.class, new FakeMethodPolicyDeserializer());
    }

}
