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

import java.util.concurrent.Executors;

@Builder
public class ContextOtpConfig {

    @Builder.Default
    private final StubSimSwapExecutorConfig simSwapExecutorConfig = buildDefaultStubConfig();

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

    private static StubSimSwapExecutorConfig buildDefaultStubConfig() {
        return StubSimSwapExecutorConfig.builder()
                .executor(Executors.newFixedThreadPool(2))
                .build();
    }

}
