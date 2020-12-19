package uk.co.idv.common.adapter.protector;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.function.UnaryOperator;

@RequiredArgsConstructor
public class FakeMasker implements UnaryOperator<String> {

    private final char maskChar;

    @Override
    public String apply(String value) {
        return StringUtils.repeat(maskChar, value.length());
    }

}
