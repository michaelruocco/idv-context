package uk.co.idv.identity.usecases.eligibility.external.data;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.usecases.async.FutureWaiter;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

@Builder
@Slf4j
public class AsyncDataLoader {

    private final Executor executor;
    private final DataSupplierFactory supplierFactory;
    private final FutureWaiter futureWaiter;

    public DataFutures loadData(AsyncDataLoadRequest request) {
        DataFutures futures = initiateLoad(request);
        futureWaiter.waitFor(futures.allCombined(), request.getTimeout());
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

}
