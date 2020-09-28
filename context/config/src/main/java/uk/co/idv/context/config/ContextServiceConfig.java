package uk.co.idv.context.config;

import lombok.Builder;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.common.usecases.id.RandomIdGenerator;
import uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap.StubSimSwapExecutorConfig;
import uk.co.idv.context.config.repository.ParentContextRepositoryConfig;
import uk.co.idv.context.usecases.context.ContextService;
import uk.co.idv.context.usecases.context.CreateContextRequestConverter;
import uk.co.idv.context.usecases.context.method.CompositeMethodBuilder;
import uk.co.idv.context.usecases.context.method.MethodBuilder;
import uk.co.idv.context.usecases.context.method.MethodsBuilder;
import uk.co.idv.context.usecases.context.method.otp.OtpBuilder;
import uk.co.idv.context.usecases.context.method.otp.delivery.CompositeDeliveryMethodConfigConverter;
import uk.co.idv.context.usecases.context.method.otp.delivery.DeliveryMethodConfigsConverter;
import uk.co.idv.context.usecases.context.method.otp.delivery.email.EmailDeliveryMethodConfigConverter;
import uk.co.idv.context.usecases.context.method.otp.delivery.phone.OtpPhoneNumberConverter;
import uk.co.idv.context.usecases.context.method.otp.delivery.phone.OtpPhoneNumberEligibilityCalculator;
import uk.co.idv.context.usecases.context.method.otp.delivery.phone.OtpPhoneNumbersConverter;
import uk.co.idv.context.usecases.context.method.otp.delivery.phone.PhoneDeliveryMethodConfigConverter;
import uk.co.idv.context.usecases.context.sequence.SequenceBuilder;
import uk.co.idv.context.usecases.context.sequence.SequencesBuilder;
import uk.co.idv.context.usecases.policy.ContextPolicyService;

import java.time.Clock;

@Builder
public class ContextServiceConfig {

    private final ParentContextRepositoryConfig repositoryConfig;
    private final StubSimSwapExecutorConfig simSwapExecutorConfig;

    @Builder.Default
    private final IdGenerator idGenerator = new RandomIdGenerator();

    @Builder.Default
    private final Clock clock = Clock.systemUTC();

    public ContextService contextService() {
        return ContextService.builder()
                .clock(clock)
                .repository(repositoryConfig.contextRepository())
                .requestConverter(serviceCreateContextRequestConverter())
                .sequencesBuilder(sequencesBuilder())
                .build();
    }

    public ContextPolicyService policyService() {
        return new ContextPolicyService(repositoryConfig.policyRepository());
    }

    private CreateContextRequestConverter serviceCreateContextRequestConverter() {
        return new CreateContextRequestConverter(idGenerator);
    }

    private SequencesBuilder sequencesBuilder() {
        return new SequencesBuilder(new SequenceBuilder(methodsBuilder()));
    }

    private MethodsBuilder methodsBuilder() {
        return new MethodsBuilder(new CompositeMethodBuilder(otpBuilder()));
    }

    private MethodBuilder otpBuilder() {
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
                .idGenerator(idGenerator)
                .eligibilityCalculator(otpPhoneNumberEligibilityCalculator())
                .build();
    }

    private OtpPhoneNumberEligibilityCalculator otpPhoneNumberEligibilityCalculator() {
        return OtpPhoneNumberEligibilityCalculator.builder()
                .clock(clock)
                .simSwapExecutor(simSwapExecutorConfig.simSwapExecutor())
                .build();
    }

}
