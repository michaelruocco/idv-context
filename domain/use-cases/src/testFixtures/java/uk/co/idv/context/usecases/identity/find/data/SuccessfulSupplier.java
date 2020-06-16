package uk.co.idv.context.usecases.identity.find.data;

import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class SuccessfulSupplier<T> implements Supplier<T> {

    private final T dataToReturn;

    @Override
    public T get() {
        return dataToReturn;
    }

}
