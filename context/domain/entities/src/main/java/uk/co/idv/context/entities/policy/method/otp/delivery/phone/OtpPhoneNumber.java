package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

import uk.co.idv.context.entities.policy.method.otp.delivery.Updatable;

public interface OtpPhoneNumber extends Updatable {

    boolean isInternational();

    boolean isMobile();

}
