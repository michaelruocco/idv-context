package uk.co.idv.identity.entities.protect;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.UnaryOperator;

@RequiredArgsConstructor
public class TypedProtector<T> implements UnaryOperator<String> {

    @Getter
    private final Class<T> type;
    private final UnaryOperator<String> protector;

    @Override
    public String apply(String value) {
        return protector.apply(value);
    }

}
