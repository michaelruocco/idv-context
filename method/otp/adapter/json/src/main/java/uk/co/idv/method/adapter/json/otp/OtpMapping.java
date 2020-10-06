package uk.co.idv.method.adapter.json.otp;

import com.fasterxml.jackson.databind.Module;
import uk.co.idv.method.adapter.json.MethodMapping;
import uk.co.idv.method.adapter.json.otp.policy.OtpPolicyModule;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.policy.OtpPolicy;
import uk.co.idv.method.entities.policy.MethodPolicy;

import java.util.Arrays;
import java.util.Collection;

public class OtpMapping implements MethodMapping {

    @Override
    public String getName() {
        return "one-time-passcode";
    }

    @Override
    public Class<? extends Method> getMethodType() {
        return Otp.class;
    }

    @Override
    public Class<? extends MethodPolicy> getPolicyType() {
        return OtpPolicy.class;
    }

    public Collection<Module> getModules() {
        return Arrays.asList(new OtpModule(), new OtpPolicyModule());
    }

}
