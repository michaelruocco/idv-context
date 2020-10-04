package uk.co.idv.context.entities.context.method;

import uk.co.idv.context.entities.context.method.otp.OtpMother;
import uk.co.idv.method.entities.method.Method;

public interface MethodsMother {

    static Methods otpOnly() {
        return new Methods(OtpMother.build());
    }

    static Methods empty() {
        return new Methods();
    }

    static Methods withMethods(Method... methods) {
        return new Methods(methods);
    }

}
