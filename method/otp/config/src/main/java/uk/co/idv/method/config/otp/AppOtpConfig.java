package uk.co.idv.method.config.otp;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.usecases.context.ContextMethodUpdater;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.idv.method.adapter.otp.protect.mask.OtpMasker;
import uk.co.idv.method.config.AppMethodConfig;
import uk.co.idv.method.usecases.MethodBuilder;
import uk.co.idv.method.usecases.protect.MethodProtector;
import uk.co.idv.method.usecases.otp.OtpBuilder;
import uk.co.idv.method.usecases.otp.delivery.CompositeDeliveryMethodConfigConverter;
import uk.co.idv.method.usecases.otp.delivery.DeliveryMethodConfigsConverter;
import uk.co.idv.method.usecases.otp.delivery.email.EmailDeliveryMethodConfigConverter;
import uk.co.idv.method.usecases.otp.delivery.phone.OtpPhoneNumberConverter;
import uk.co.idv.method.usecases.otp.delivery.phone.OtpPhoneNumberEligibilityCalculator;
import uk.co.idv.method.usecases.otp.delivery.phone.OtpPhoneNumbersConverter;
import uk.co.idv.method.usecases.otp.delivery.phone.PhoneDeliveryMethodConfigConverter;
import uk.co.idv.method.usecases.otp.delivery.phone.simswap.SimSwapExecutorConfig;
import uk.co.idv.method.usecases.otp.simswap.SimSwap;
import uk.co.idv.method.usecases.otp.simswap.async.AsyncSimSwap;
import uk.co.idv.method.usecases.otp.simswap.async.AsyncSimSwapUpdateContextTaskFactory;
import uk.co.idv.method.usecases.otp.simswap.sync.SyncSimSwap;
import uk.co.mruoc.randomvalue.uuid.RandomUuidGenerator;
import uk.co.mruoc.randomvalue.uuid.UuidGenerator;

import java.time.Clock;
import java.util.concurrent.Executors;

@Slf4j
@Builder
public class AppOtpConfig implements AppMethodConfig {

    private final SimSwapExecutorConfig simSwapExecutorConfig;
    private final ContextRepository contextRepository;

    @Builder.Default
    private final Clock clock = Clock.systemUTC();

    @Builder.Default
    private final UuidGenerator uuidGenerator = new RandomUuidGenerator();

    @Override
    public MethodBuilder methodBuilder() {
        return OtpBuilder.builder()
                .configsConverter(deliveryMethodConfigsConverter())
                .simSwap(simSwap())
                .build();
    }

    @Override
    public MethodProtector methodProtector() {
        return new OtpMasker();
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
                .uuidGenerator(uuidGenerator)
                .eligibilityCalculator(otpPhoneNumberEligibilityCalculator())
                .build();
    }

    private OtpPhoneNumberEligibilityCalculator otpPhoneNumberEligibilityCalculator() {
        return OtpPhoneNumberEligibilityCalculator.builder()
                .clock(clock)
                .simSwapExecutor(simSwapExecutorConfig.simSwapExecutor())
                .build();
    }

    private SimSwap simSwap() {
        return SimSwap.builder()
                .async(asyncSimSwap())
                .sync(new SyncSimSwap())
                .build();
    }

    private AsyncSimSwap asyncSimSwap() {
        return AsyncSimSwap.builder()
                .taskFactory(simSwapUpdateContextTaskFactory())
                .executor(Executors.newScheduledThreadPool(loadAsyncSimSwapThreadPoolSize()))
                .build();
    }

    private AsyncSimSwapUpdateContextTaskFactory simSwapUpdateContextTaskFactory() {
        return AsyncSimSwapUpdateContextTaskFactory.builder()
                .updater(new ContextMethodUpdater(contextRepository))
                .syncStrategy(new SyncSimSwap())
                .build();
    }

    private static int loadAsyncSimSwapThreadPoolSize() {
        var key = "async.sim.swap.thread.pool.size";
        var size = Integer.parseInt(System.getProperty(key, "50"));
        log.info("loaded {} value {}", key, size);
        return size;
    }

}
