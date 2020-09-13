package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

public interface OtpPhoneNumbersMother {

    static OtpPhoneNumbers with(OtpPhoneNumber... numbers) {
        return new OtpPhoneNumbers(numbers);
    }

    static OtpPhoneNumbers localMobileAndFixed() {
        return with(
                OtpPhoneNumberMother.localMobile(),
                OtpPhoneNumberMother.localFixedLine()
        );
    }

    static OtpPhoneNumbers internationalMobileAndFixed() {
        return with(
                OtpPhoneNumberMother.internationalMobile(),
                OtpPhoneNumberMother.internationalFixedLine()
        );
    }

    static OtpPhoneNumbers localAndInternationalMobiles() {
        return with(
                OtpPhoneNumberMother.localMobile(),
                OtpPhoneNumberMother.internationalMobile()
        );
    }

    static OtpPhoneNumbers local() {
        return with(
                OtpPhoneNumberMother.localMobile(),
                OtpPhoneNumberMother.localFixedLine()
        );
    }

    static OtpPhoneNumbers empty() {
        return with();
    }

}
