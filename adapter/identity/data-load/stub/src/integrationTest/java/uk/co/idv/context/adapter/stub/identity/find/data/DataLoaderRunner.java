package uk.co.idv.context.adapter.stub.identity.find.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.usecases.identity.find.data.AsyncDataLoadRequest;
import uk.co.idv.context.usecases.identity.find.data.AsyncDataLoadRequestMother;
import uk.co.idv.context.usecases.identity.find.data.AsyncDataLoader;
import uk.co.idv.context.usecases.identity.find.data.DataFutures;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

import static uk.co.idv.context.usecases.identity.find.data.DurationCalculator.millisBetween;

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
        log.info("{} took {}ms", i, millisBetween(start, end));
        return futures;
    }

    private static AsyncDataLoadRequest buildRequest(Duration timeout) {
        return AsyncDataLoadRequestMother.builder()
                .timeout(timeout)
                .build();
    }

}
