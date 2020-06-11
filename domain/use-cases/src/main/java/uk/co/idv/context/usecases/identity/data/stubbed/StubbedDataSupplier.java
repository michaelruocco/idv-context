package uk.co.idv.context.usecases.identity.data.stubbed;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.usecases.identity.LoadIdentityRequest;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

import static uk.co.idv.context.usecases.identity.data.DurationCalculator.millisBetween;

@RequiredArgsConstructor
@Slf4j
public class StubbedDataSupplier<T> implements Supplier<T> {

    private final LoadIdentityRequest request;
    private final Duration delay;
    private final StubbedDataFactory<T> factory;
    private final Sleeper sleeper;

    @Override
    public T get() {
        if (shouldReturnStubbedData()) {
            return loadStubbedData();
        }
        return loadEmptyData();
    }

    private boolean shouldReturnStubbedData() {
        return !request.getProvidedAlias().getValue().endsWith("9");
    }

    private T loadStubbedData() {
        String name = factory.getName();
        Instant start = Instant.now();
        log.debug("loading stubbed {} data with delay {}", name, delay);
        sleeper.sleep(delay);
        T data = factory.getPopulatedData();
        Instant end = Instant.now();
        log.debug("returning stubbed {} data {} took {}ms", name, data, millisBetween(start, end));
        return data;
    }

    private T loadEmptyData() {
        log.debug("loading empty {} data", factory.getName());
        return factory.getEmptyData();
    }

}
