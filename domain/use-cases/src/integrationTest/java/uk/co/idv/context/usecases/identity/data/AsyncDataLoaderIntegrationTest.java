package uk.co.idv.context.usecases.identity.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.CreditCardNumberMother;
import uk.co.idv.context.usecases.identity.DefaultLoadIdentityRequest;
import uk.co.idv.context.usecases.identity.LoadIdentityRequest;
import uk.co.idv.context.usecases.identity.data.stubbed.StubbedDataSupplierFactory;

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
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.idv.context.usecases.identity.data.DurationCalculator.millisBetween;

@Slf4j
public class AsyncDataLoaderIntegrationTest {

    private static final Duration TIMEOUT = Duration.ofMillis(1500);
    private static final int NUMBER_OF_RUNS = 100;

    @Test
    void shouldLoadStubbedData() {
        ExecutorService executor = Executors.newCachedThreadPool();
        DataSupplierFactory supplierFactory = new StubbedDataSupplierFactory();
        AsyncDataLoader loader = new AsyncDataLoader(executor, supplierFactory);
        Instant start = Instant.now();

        try {
            Collection<Future<DataFutures>> futures = IntStream.range(0, NUMBER_OF_RUNS)
                    .mapToObj(i -> CompletableFuture.supplyAsync(new Runner(i, TIMEOUT, loader)))
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
                .map(AsyncDataLoaderIntegrationTest::getResultSafely)
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

    @Slf4j
    @RequiredArgsConstructor
    private static class Runner implements Supplier<DataFutures> {

        private final int i;
        private final Duration timeout;
        private final AsyncDataLoader loader;

        @Override
        public DataFutures get() {
            Instant start = Instant.now();
            LoadIdentityRequest request = buildRequest(timeout);
            DataFutures futures = loader.loadData(request);
            Instant end = Instant.now();
            log.info("{} took {}ms", i, millisBetween(start, end));
            return futures;
        }

        private static LoadIdentityRequest buildRequest(Duration timeout) {
            return DefaultLoadIdentityRequest.builder()
                    .providedAlias(CreditCardNumberMother.creditCardNumber())
                    .dataLoadTimeout(timeout)
                    .build();
        }

    }

}
