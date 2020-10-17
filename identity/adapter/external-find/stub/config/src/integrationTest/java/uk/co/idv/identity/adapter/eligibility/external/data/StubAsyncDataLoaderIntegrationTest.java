package uk.co.idv.identity.adapter.eligibility.external.data;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.usecases.async.FutureWaiter;
import uk.co.idv.identity.adapter.eligibility.external.StubExternalFindIdentityConfig;
import uk.co.idv.identity.usecases.eligibility.external.data.AsyncDataLoader;
import uk.co.idv.identity.usecases.eligibility.external.data.DataFutures;
import uk.co.idv.identity.usecases.eligibility.external.data.DataSupplierFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class StubAsyncDataLoaderIntegrationTest {

    private static final Duration TIMEOUT = Duration.ofMillis(1750);
    private static final Duration PHONE_NUMBER_DELAY = Duration.ofMillis(500);
    private static final Duration EMAIL_ADDRESS_DELAY = Duration.ofMillis(1500);
    private static final int NUMBER_OF_RUNS = 100;

    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(NUMBER_OF_RUNS * 2);

    @Test
    void shouldLoadStubbedData() {
        StubExternalFindIdentityConfig config = StubExternalFindIdentityConfig.builder()
                .executor(EXECUTOR)
                .phoneNumberDelay(PHONE_NUMBER_DELAY)
                .emailAddressDelay(EMAIL_ADDRESS_DELAY)
                .build();
        DataSupplierFactory supplierFactory = toSupplierFactory(config);
        AsyncDataLoader loader = AsyncDataLoader.builder()
                .executor(config.getExecutor())
                .supplierFactory(supplierFactory)
                .futureWaiter(new FutureWaiter())
                .build();

        Instant start = Instant.now();
        try {
            loadStubbedData(loader);
        } finally {
            EXECUTOR.shutdown();
            Instant end = Instant.now();
            Duration duration = Duration.between(start, end);
            log.info("took {}ms", duration.toMillis());
            assertThat(duration).isLessThan(TIMEOUT.plusSeconds(3));
        }
    }

    private static void loadStubbedData(AsyncDataLoader loader) {
        Collection<Future<DataFutures>> futures = IntStream.range(0, NUMBER_OF_RUNS)
                .mapToObj(i -> CompletableFuture.supplyAsync(new DataLoaderRunner(i, TIMEOUT, loader)))
                .collect(Collectors.toList());
        Collection<DataFutures> dataFutures = getResultsSafely(futures);

        assertThat(countTimesAddressesLoaded(dataFutures)).isEqualTo(NUMBER_OF_RUNS);
        assertThat(countTimesPhoneNumbersLoaded(dataFutures)).isEqualTo(NUMBER_OF_RUNS);
    }

    public static DataSupplierFactory toSupplierFactory(StubExternalFindIdentityConfig config) {
        return StubDataSupplierFactory.builder()
                .phoneNumberDelay(config.getPhoneNumberDelay())
                .emailAddressDelay(config.getEmailAddressDelay())
                .build();
    }

    private static Collection<DataFutures> getResultsSafely(Collection<Future<DataFutures>> futures) {
        return futures.stream()
                .map(StubAsyncDataLoaderIntegrationTest::getResultSafely)
                .collect(Collectors.toList());
    }

    private static DataFutures getResultSafely(Future<DataFutures> future) {
        try {
            return future.get(TIMEOUT.plusSeconds(30).toMillis(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new AsyncResultsException(e);
        }
    }

    private static int countTimesAddressesLoaded(Collection<DataFutures> futures) {
        return futures.stream()
                .map(future -> future.getEmailAddressesNow().isEmpty() ? 0 : 1)
                .reduce(0, Integer::sum);
    }

    private static int countTimesPhoneNumbersLoaded(Collection<DataFutures> futures) {
        return futures.stream()
                .map(future -> future.getPhoneNumbersNow().isEmpty() ? 0 : 1)
                .reduce(0, Integer::sum);
    }

}
