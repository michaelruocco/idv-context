package uk.co.idv.context.usecases.identity.service.find.data;

import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class DelayedSupplier<T> implements Supplier<T> {

    private final Delay delay;
    private final T dataToReturn;

    public DelayedSupplier(Duration delayDuration, T dataToReturn) {
        this(new Delay(delayDuration), dataToReturn);
    }

    @Override
    public T get() {
        delay.execute();
        return dataToReturn;
    }

}
