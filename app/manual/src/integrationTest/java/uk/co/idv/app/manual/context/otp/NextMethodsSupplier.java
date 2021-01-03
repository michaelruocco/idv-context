package uk.co.idv.app.manual.context.otp;

import lombok.Builder;
import uk.co.idv.app.manual.Application;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.method.entities.method.Method;

import java.util.UUID;
import java.util.function.Supplier;

@Builder
public class NextMethodsSupplier implements Supplier<Iterable<Method>> {

    private final Application application;
    private final UUID contextId;

    @Override
    public Iterable<Method> get() {
        Context context = application.findContext(contextId);
        return context.getNextMethods();
    }

}
