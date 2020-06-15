package uk.co.idv.context.usecases.identity.service.find.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Slf4j
public class AsyncDataLoader {

    private final Executor executor;
    private final DataSupplierFactory supplierFactory;

    public DataFutures loadData(AsyncDataLoadRequest request) {
        DataFutures futures = initiateLoad(request);
        waitFor(request.getTimeout(), futures.toArray());
        return futures;
    }

    private DataFutures initiateLoad(AsyncDataLoadRequest request) {
        log.info("initiating async data loads for request {}", request);
        return DataFutures.builder()
                .phoneNumbers(loadPhoneNumbers(request))
                .emailAddresses(loadEmailAddresses(request))
                .build();
    }

    private CompletableFuture<PhoneNumbers> loadPhoneNumbers(AsyncDataLoadRequest request) {
        return supplyAsync(supplierFactory.phoneNumberSupplier(request));
    }

    private CompletableFuture<EmailAddresses> loadEmailAddresses(AsyncDataLoadRequest request) {
        return supplyAsync(supplierFactory.emailAddressSupplier(request));
    }

    private <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, executor);
    }

    private void waitFor(Duration timeout, CompletableFuture<?>... futures) {
        log.info("waiting for futures to complete with timeout {}", timeout);
        Instant start = Instant.now();
        try {
            CompletableFuture<Void> combined = CompletableFuture.allOf(futures);
            combined.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.debug(e.getMessage(), e);
        } finally {
            Instant end = Instant.now();
            log.info("took {}ms for futures to complete", millisBetween(start, end));
        }
    }

    private static long millisBetween(final Instant start, final Instant end) {
        return Duration.between(start, end).toMillis();
    }

}
