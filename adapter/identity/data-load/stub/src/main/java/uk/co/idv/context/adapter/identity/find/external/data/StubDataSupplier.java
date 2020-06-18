package uk.co.idv.context.adapter.identity.find.external.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.usecases.identity.find.data.Delay;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Slf4j
public class StubDataSupplier<T> implements Supplier<T> {

    private final Aliases aliases;
    private final Delay delay;
    private final StubDataFactory<T> factory;

    @Override
    public T get() {
        if (shouldReturnStubbedData()) {
            return loadStubbedData();
        }
        return loadEmptyData();
    }

    private boolean shouldReturnStubbedData() {
        return !aliases.hasAnyValuesEndingWith("9");
    }

    private T loadStubbedData() {
        String name = factory.getName();
        Instant start = Instant.now();
        delay.execute();
        T data = factory.getPopulatedData();
        Instant end = Instant.now();
        log.debug("returning stubbed {} data {} took {}ms", name, data, millisBetween(start, end));
        return data;
    }

    private T loadEmptyData() {
        log.debug("loading empty {} data", factory.getName());
        return factory.getEmptyData();
    }

    private static long millisBetween(final Instant start, final Instant end) {
        return Duration.between(start, end).toMillis();
    }

}
