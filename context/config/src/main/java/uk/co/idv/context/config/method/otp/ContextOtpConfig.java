package uk.co.idv.context.config.method.otp;

import lombok.Builder;
import uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap.StubSimSwapExecutorConfig;
import uk.co.idv.context.usecases.context.method.MethodBuilder;
import uk.co.idv.context.usecases.context.method.otp.OtpBuilder;
import uk.co.idv.context.usecases.context.method.otp.delivery.CompositeDeliveryMethodConfigConverter;
import uk.co.idv.context.usecases.context.method.otp.delivery.DeliveryMethodConfigsConverter;
import uk.co.idv.context.usecases.context.method.otp.delivery.email.EmailDeliveryMethodConfigConverter;
import uk.co.idv.context.usecases.context.method.otp.delivery.phone.OtpPhoneNumberConverter;
import uk.co.idv.context.usecases.context.method.otp.delivery.phone.OtpPhoneNumberEligibilityCalculator;
import uk.co.idv.context.usecases.context.method.otp.delivery.phone.OtpPhoneNumbersConverter;
import uk.co.idv.context.usecases.context.method.otp.delivery.phone.PhoneDeliveryMethodConfigConverter;

@Builder
public class ContextOtpConfig {

    private final StubSimSwapExecutorConfig simSwapExecutorConfig;

    public MethodBuilder otpBuilder() {
        return OtpBuilder.builder()
                .configsConverter(deliveryMethodConfigsConverter())
                .build();
    }

    private DeliveryMethodConfigsConverter deliveryMethodConfigsConverter() {
        return new DeliveryMethodConfigsConverter(new CompositeDeliveryMethodConfigConverter(
                phoneDeliveryMethodConfigConverter(),
                new EmailDeliveryMethodConfigConverter()
        ));
    }

    private PhoneDeliveryMethodConfigConverter phoneDeliveryMethodConfigConverter() {
        return PhoneDeliveryMethodConfigConverter.builder()
                .otpNumbersConverter(new OtpPhoneNumbersConverter(otpPhoneNumberConverter()))
                .build();
    }

    private OtpPhoneNumberConverter otpPhoneNumberConverter() {
        return OtpPhoneNumberConverter.builder()
                .eligibilityCalculator(otpPhoneNumberEligibilityCalculator())
                .build();
    }

    private OtpPhoneNumberEligibilityCalculator otpPhoneNumberEligibilityCalculator() {
        return OtpPhoneNumberEligibilityCalculator.builder()
                .simSwapExecutor(simSwapExecutorConfig.simSwapExecutor())
                .build();
    }

}
