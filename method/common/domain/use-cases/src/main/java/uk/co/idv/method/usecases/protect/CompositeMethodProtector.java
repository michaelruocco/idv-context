package uk.co.idv.method.usecases.protect;

import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.method.Method;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.UnaryOperator;

@RequiredArgsConstructor
public class CompositeMethodProtector implements UnaryOperator<Method> {

    private final Collection<MethodProtector> protectors;

    public CompositeMethodProtector(MethodProtector... protectors) {
        this(Arrays.asList(protectors));
    }

    @Override
    public Method apply(Method method) {
        return findMethodProtector(method).apply(method);
    }

    private MethodProtector findMethodProtector(Method method) {
        return protectors.stream()
                .filter(protector -> protector.supports(method.getName()))
                .findFirst()
                .orElseThrow(() -> new MethodProtectionNotSupportedException(method.getName()));
    }

}
