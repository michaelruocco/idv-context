package uk.co.idv.context.adapter.identity.data.stub;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.usecases.identity.data.AsyncDataLoader;
import uk.co.idv.context.usecases.identity.data.DataFutures;
import uk.co.idv.context.usecases.identity.data.DataSupplierFactory;

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
public class StubAsyncDataLoaderIntegrationTest {

    private static final Duration TIMEOUT = Duration.ofMillis(1750);
    private static final int NUMBER_OF_RUNS = 100;

    @Test
    void shouldLoadStubbedData() {
        ExecutorService executor = Executors.newCachedThreadPool();
        DataSupplierFactory supplierFactory = new StubDataSupplierFactory();
        AsyncDataLoader loader = new AsyncDataLoader(executor, supplierFactory);
        Instant start = Instant.now();

        try {
            Collection<Future<DataFutures>> futures = IntStream.range(0, NUMBER_OF_RUNS)
                    .mapToObj(i -> CompletableFuture.supplyAsync(new DataLoaderRunner(i, TIMEOUT, loader)))
                    .collect(Collectors.toList());
            Collection<DataFutures> dataFutures = getResultsSafely(futures);

            assertThat(countTimesAddressesLoaded(dataFutures)).isEqualTo(NUMBER_OF_RUNS);
            assertThat(countTimesPhoneNumbersLoaded(dataFutures)).isEqualTo(NUMBER_OF_RUNS);
        } finally {
            executor.shutdown();
            Instant end = Instant.now();
            Duration duration = Duration.between(start, end);
            log.info("took {}ms", duration.toMillis());
            assertThat(duration).isLessThan(TIMEOUT.plusMillis(1500));
        }
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
            throw new RuntimeException(e);
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
