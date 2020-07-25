package uk.co.idv.context.usecases.eligibility.external.data;

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

import static uk.co.idv.context.usecases.util.DurationCalculator.millisBetweenNowAnd;

@RequiredArgsConstructor
@Slf4j
public class AsyncDataLoader {

    private final Executor executor;
    private final DataSupplierFactory supplierFactory;

    public DataFutures loadData(AsyncDataLoadRequest request) {
        DataFutures futures = initiateLoad(request);
        waitFor(request.getTimeout(), futures.allCombined());
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

    private void waitFor(Duration timeout, CompletableFuture<Void> future) {
        log.info("waiting for futures to complete with timeout {}", timeout);
        Instant start = Instant.now();
        try {
            future.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.debug(e.getMessage(), e);
        } catch (ExecutionException | TimeoutException e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info("futures took {}ms to complete", millisBetweenNowAnd(start));
        }
    }

}
