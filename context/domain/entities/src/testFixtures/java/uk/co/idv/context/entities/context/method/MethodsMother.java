package uk.co.idv.context.entities.context.method;

import uk.co.idv.context.entities.context.method.otp.OtpMother;

public interface MethodsMother {

    static Methods otpOnly() {
        return new Methods(OtpMother.build());
    }

}
