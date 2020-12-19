package uk.co.idv.common.adapter.protector;

import lombok.RequiredArgsConstructor;

import java.util.function.UnaryOperator;

@RequiredArgsConstructor
public class DataProtector<T> implements UnaryOperator<String> {

    private final Class<T> type;
    private final UnaryOperator<String> protector;

    public boolean supports(Class<?> otherType) {
        return type.isAssignableFrom(otherType);
    }

    @Override
    public String apply(String value) {
        return protector.apply(value);
    }

}
