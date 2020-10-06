package uk.co.idv.method.usecases.otp.delivery.phone;

import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumber;
import uk.co.mruoc.localphone.LocalNumberCalculator;
import uk.co.mruoc.localphone.LocalPhoneNumber;

@RequiredArgsConstructor
public class PhoneNumberConverter {

    private final LocalNumberCalculator calculator;

    public PhoneNumberConverter() {
        this(new LocalNumberCalculator());
    }

    public OtpPhoneNumber toOtpPhoneNumber(PhoneNumber number, CountryCode country) {
        LocalPhoneNumber localNumber = calculator.toLocalPhoneNumber(number.getValue(), country);
        return OtpPhoneNumber.builder()
                .lastUpdated(number.getLastUpdated().orElse(null))
                .local(localNumber.isLocal())
                .mobile(localNumber.isMobile())
                .value(localNumber.getFormattedValue())
                .build();
    }

}
