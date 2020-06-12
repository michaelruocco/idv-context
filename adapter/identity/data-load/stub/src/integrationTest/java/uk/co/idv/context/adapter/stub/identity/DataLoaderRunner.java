package uk.co.idv.context.adapter.stub.identity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.alias.CreditCardNumberMother;
import uk.co.idv.context.usecases.identity.DefaultFindIdentityRequest;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;
import uk.co.idv.context.usecases.identity.data.AsyncDataLoader;
import uk.co.idv.context.usecases.identity.data.DataFutures;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

import static uk.co.idv.context.usecases.identity.data.DurationCalculator.millisBetween;

@Slf4j
@RequiredArgsConstructor
public class DataLoaderRunner implements Supplier<DataFutures> {

    private final int i;
    private final Duration timeout;
    private final AsyncDataLoader loader;

    @Override
    public DataFutures get() {
        Instant start = Instant.now();
        FindIdentityRequest request = buildRequest(timeout);
        DataFutures futures = loader.loadData(request);
        Instant end = Instant.now();
        log.info("{} took {}ms", i, millisBetween(start, end));
        return futures;
    }

    private static FindIdentityRequest buildRequest(Duration timeout) {
        return DefaultFindIdentityRequest.builder()
                .providedAlias(CreditCardNumberMother.creditCardNumber())
                .dataLoadTimeout(timeout)
                .build();
    }

}
