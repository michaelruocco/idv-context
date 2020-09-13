package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumber.OtpPhoneNumberBuilder;

import java.time.Instant;

public interface OtpPhoneNumberMother {

    static OtpPhoneNumber localMobile() {
        return builder().build();
    }

    static OtpPhoneNumber localFixedLine() {
        return builder()
                .value("+1604123123")
                .mobile(false)
                .build();
    }

    static OtpPhoneNumber internationalMobile() {
        return builder()
                .value("+15417543010")
                .local(false)
                .build();
    }

    static OtpPhoneNumber internationalFixedLine() {
        return builder()
                .value("++15417543010")
                .local(false)
                .mobile(false)
                .build();
    }

    static OtpPhoneNumber withoutLastUpdated() {
        return builder().lastUpdated(null).build();
    }

    static OtpPhoneNumber withValue(String value) {
        return builder().value(value).build();
    }

    static OtpPhoneNumberBuilder builder() {
        return OtpPhoneNumber.builder()
                .value("+447089111111")
                .lastUpdated(Instant.parse("2020-08-29T20:55:12.825Z"))
                .mobile(true)
                .local(true);
    }

}
