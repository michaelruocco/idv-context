package uk.co.idv.method.usecases.otp.delivery.phone;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;
import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumbers;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfig;
import uk.co.idv.method.entities.otp.policy.delivery.phone.PhoneDeliveryMethodConfig;
import uk.co.idv.method.usecases.otp.delivery.DeliveryMethodConfigConverter;

@Builder
@Slf4j
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
        log.debug("converted numbers {} into otp numbers {}", identity.getPhoneNumbers(), numbers);
        DeliveryMethods deliveryMethods = otpNumbersConverter.toDeliveryMethods(numbers, phoneConfig);
        log.debug("converted numbers into delivery methods {}", deliveryMethods);
        return deliveryMethods;
    }

}
