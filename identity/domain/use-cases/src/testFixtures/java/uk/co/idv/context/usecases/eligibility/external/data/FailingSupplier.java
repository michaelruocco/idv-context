package uk.co.idv.context.usecases.eligibility.external.data;

import java.util.function.Supplier;

public class FailingSupplier<T> implements Supplier<T> {

    @Override
    public T get() {
        throw new SupplierFailedException("failing supplier");
    }

}
