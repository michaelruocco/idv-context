package uk.co.idv.context.adapter.eligibility.external.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.usecases.eligibility.external.data.AsyncDataLoadRequest;
import uk.co.idv.identity.usecases.eligibility.external.data.AsyncDataLoadRequestMother;
import uk.co.idv.identity.usecases.eligibility.external.data.AsyncDataLoader;
import uk.co.idv.identity.usecases.eligibility.external.data.DataFutures;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

import static uk.co.idv.common.usecases.duration.DurationCalculator.millisBetweenNowAnd;

@Slf4j
@RequiredArgsConstructor
public class DataLoaderRunner implements Supplier<DataFutures> {

    private final int i;
    private final Duration timeout;
    private final AsyncDataLoader loader;

    @Override
    public DataFutures get() {
        Instant start = Instant.now();
        AsyncDataLoadRequest request = buildRequest(timeout);
        DataFutures futures = loader.loadData(request);
        log.debug("{} took {}ms", i, millisBetweenNowAnd(start));
        return futures;
    }

    private static AsyncDataLoadRequest buildRequest(Duration timeout) {
        return AsyncDataLoadRequestMother.builder()
                .timeout(timeout)
                .build();
    }

}
