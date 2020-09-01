package uk.co.idv.context.usecases.policy.method.otp.delivery.phone;

import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.phonenumber.PhoneNumber;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumber;
import uk.co.mruoc.localphone.LocalNumberCalculator;
import uk.co.mruoc.localphone.LocalPhoneNumber;

@RequiredArgsConstructor
public class PhoneNumberConverter {

    private final LocalNumberCalculator calculator;

    public OtpPhoneNumber toOtpPhoneNumber(CountryCode country, PhoneNumber number) {
        LocalPhoneNumber localNumber = calculator.toLocalPhoneNumber(number.getValue(), country);
        return OtpPhoneNumber.builder()
                .lastUpdated(number.getLastUpdated().orElse(null))
                .local(localNumber.isLocal())
                .mobile(localNumber.isMobile())
                .value(localNumber.getFormattedValue())
                .build();
    }

}
