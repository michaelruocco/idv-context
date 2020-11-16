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

    @Builder.Default
    private final ExecutorService executor = buildEligibilityExecutor();

    @Builder.Default
    private final Duration phoneNumberDelay = Duration.ofSeconds(0);

    @Builder.Default
    private final Duration emailAddressDelay = Duration.ofSeconds(0);

    @Builder.Default private final Duration timeout = Duration.ofSeconds(2);

    public static StubExternalFindIdentityConfig build() {
        return build(buildEligibilityExecutor());
    }

    public static StubExternalFindIdentityConfig build(ExecutorService executor) {
        return StubExternalFindIdentityConfig.builder()
                .executor(executor)
                .timeout(Duration.ofMillis(2000))
                .phoneNumberDelay(Duration.ofMillis(1500))
                .emailAddressDelay(Duration.ofMillis(1000))
                .build();
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
                .build();
    }

    private static ExecutorService buildEligibilityExecutor() {
        return Executors.newCachedThreadPool();
        //return Executors.newFixedThreadPool(loadThreadPoolSize());
    }

    private static int loadThreadPoolSize() {
        String key = "external.find.identity.thread.pool.size";
        int size = Integer.parseInt(System.getProperty(key, "100"));
        log.info("loaded {} value {}", key, size);
        return size;
    }

}
