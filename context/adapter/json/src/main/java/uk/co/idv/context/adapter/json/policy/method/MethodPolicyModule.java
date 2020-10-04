package uk.co.idv.context.adapter.json.policy.method;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.policy.method.otp.OtpPolicyModule;
import uk.co.idv.context.entities.policy.method.MethodPolicies;
import uk.co.idv.method.entities.policy.MethodPolicy;

import java.util.Collections;

public class MethodPolicyModule extends SimpleModule {

    public MethodPolicyModule() {
        super("method-policy-module", Version.unknownVersion());

        setMixInAnnotation(MethodPolicies.class, MethodPoliciesMixin.class);

        addDeserializer(MethodPolicy.class, new MethodPolicyDeserializer());
        addDeserializer(MethodPolicies.class, new MethodPoliciesDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new OtpPolicyModule());
    }

}
