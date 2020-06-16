package uk.co.idv.context.adapter.identity.find.external.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.usecases.identity.find.data.AsyncDataLoadRequest;
import uk.co.idv.context.usecases.identity.find.data.AsyncDataLoadRequestMother;
import uk.co.idv.context.usecases.identity.find.data.AsyncDataLoader;
import uk.co.idv.context.usecases.identity.find.data.DataFutures;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

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
        Instant end = Instant.now();
        log.debug("{} took {}ms", i, millisBetween(start, end));
        return futures;
    }

    private static AsyncDataLoadRequest buildRequest(Duration timeout) {
        return AsyncDataLoadRequestMother.builder()
                .timeout(timeout)
                .build();
    }

    private static long millisBetween(final Instant start, final Instant end) {
        return Duration.between(start, end).toMillis();
    }

}
