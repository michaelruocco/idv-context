package uk.co.idv.identity.adapter.eligibility.external;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.adapter.eligibility.external.data.StubDataSupplierFactory;
import uk.co.idv.identity.adapter.eligibility.external.data.alias.StubAliasLoader;
import uk.co.idv.identity.config.ExternalFindIdentityConfig;
import uk.co.idv.identity.usecases.eligibility.external.ExternalFindIdentity;
import uk.co.idv.identity.usecases.eligibility.external.data.AsyncDataLoader;
import uk.co.idv.identity.usecases.eligibility.external.data.DataSupplierFactory;
import uk.co.idv.identity.usecases.eligibility.external.data.DefaultTimeoutProvider;
import uk.co.idv.identity.usecases.eligibility.external.data.FindIdentityRequestConverter;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Builder
@Data
@Slf4j
public class StubExternalFindIdentityConfig implements ExternalFindIdentityConfig {

    private final ExecutorService executor;
    private final Duration phoneNumberDelay;
    private final Duration emailAddressDelay;
    private final Duration mobileDeviceDelay;
    private final Duration timeout;

    public static StubExternalFindIdentityConfig build() {
        return defaultBuilder().build();
    }

    public static StubExternalFindIdentityConfig withNoDelays() {
        return defaultBuilder()
                .timeout(Duration.ofSeconds(1))
                .emailAddressDelay(Duration.ZERO)
                .phoneNumberDelay(Duration.ZERO)
                .mobileDeviceDelay(Duration.ZERO)
                .build();
    }

    public static StubExternalFindIdentityConfigBuilder defaultBuilder() {
        return StubExternalFindIdentityConfig.builder()
                .executor(buildEligibilityExecutor())
                .timeout(Duration.ofMillis(loadTimeout()))
                .phoneNumberDelay(Duration.ofMillis(loadPhoneNumberDelay()))
                .emailAddressDelay(Duration.ofMillis(loadEmailAddressDelay()))
                .mobileDeviceDelay(Duration.ofMillis(loadMobileDeviceDelay()));
    }

    public ExternalFindIdentity externalFindIdentity() {
        return ExternalFindIdentity.builder()
                .converter(new FindIdentityRequestConverter(new DefaultTimeoutProvider(timeout)))
                .aliasLoader(new StubAliasLoader())
                .dataLoader(asyncDataLoader())
                .build();
    }

    private AsyncDataLoader asyncDataLoader() {
        return AsyncDataLoader.builder()
                .executor(executor)
                .supplierFactory(supplierFactory())
                .build();
    }

    private DataSupplierFactory supplierFactory() {
        return StubDataSupplierFactory.builder()
                .phoneNumberDelay(phoneNumberDelay)
                .emailAddressDelay(emailAddressDelay)
                .mobileDevicesDelay(mobileDeviceDelay)
                .build();
    }

    private static ExecutorService buildEligibilityExecutor() {
        return Executors.newCachedThreadPool();
    }

    private static int loadTimeout() {
        return loadMillis("external.data.timeout", 2000);
    }

    private static int loadPhoneNumberDelay() {
        return loadMillis("external.phone.number.delay", 1500);
    }

    private static int loadEmailAddressDelay() {
        return loadMillis("external.email.address.delay", 1000);
    }

    private static int loadMobileDeviceDelay() {
        return loadMillis("external.mobile.device.delay", 900);
    }

    private static int loadMillis(String key, long defaultValue) {
        var size = Integer.parseInt(System.getProperty(key, Long.toString(defaultValue)));
        log.info("loaded {} value {}", key, size);
        return size;
    }

}
