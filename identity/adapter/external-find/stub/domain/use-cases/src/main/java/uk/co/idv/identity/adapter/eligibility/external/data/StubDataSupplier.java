package uk.co.idv.identity.adapter.eligibility.external.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import uk.co.idv.common.entities.async.Delay;
import uk.co.idv.identity.entities.alias.Aliases;

import java.time.Instant;
import java.util.Map;
import java.util.function.Supplier;

import static uk.co.mruoc.duration.calculator.DurationCalculatorUtils.millisBetweenNowAnd;

@RequiredArgsConstructor
@Slf4j
public class StubDataSupplier<T> implements Supplier<T> {

    private final Map<String, String> mdc = MDC.getCopyOfContextMap();
    private final Aliases aliases;
    private final Delay delay;
    private final StubDataFactory<T> factory;

    @Override
    public T get() {
        try {
            MDC.setContextMap(mdc);
            return loadStubbedDataIfRequired();
        } finally {
            MDC.clear();
        }
    }

    private T loadStubbedDataIfRequired() {
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
        var start = Instant.now();
        delay.execute();
        var data = factory.getPopulatedData();
        log.debug("returning stubbed {} data {} took {}ms", name, data, millisBetweenNowAnd(start));
        return data;
    }

    private T loadEmptyData() {
        log.debug("loading empty {} data", factory.getName());
        return factory.getEmptyData();
    }

}
