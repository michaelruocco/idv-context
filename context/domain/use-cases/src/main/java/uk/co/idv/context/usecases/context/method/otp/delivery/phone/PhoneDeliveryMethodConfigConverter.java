package uk.co.idv.context.usecases.context.method.otp.delivery.phone;

import lombok.Builder;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;
import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfig;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumbers;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.PhoneDeliveryMethodConfig;
import uk.co.idv.context.usecases.context.method.otp.delivery.DeliveryMethodConfigConverter;
import uk.co.idv.identity.entities.identity.Identity;

@Builder
public class PhoneDeliveryMethodConfigConverter implements DeliveryMethodConfigConverter {

    @Builder.Default
    private final PhoneNumbersConverter numbersConverter = new PhoneNumbersConverter();
    private final OtpPhoneNumbersConverter otpNumbersConverter;

    @Override
    public boolean supports(DeliveryMethodConfig config) {
        return config instanceof PhoneDeliveryMethodConfig;
    }

    @Override
    public DeliveryMethods toDeliveryMethods(Identity identity, DeliveryMethodConfig config) {
        PhoneDeliveryMethodConfig phoneConfig = (PhoneDeliveryMethodConfig) config;
        OtpPhoneNumbers numbers = numbersConverter.toOtpPhoneNumbers(identity.getPhoneNumbers(), phoneConfig.getCountry());
        return otpNumbersConverter.toDeliveryMethods(numbers, phoneConfig);
    }

}
